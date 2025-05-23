/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.OtHhppMglDaoImpl;
import co.com.claro.mgl.dao.impl.TipoOtHhppMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.TipoOtHhppMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Juan David Hernandez
 */
public class TipoOtHhppMglManager {

    public List<TipoOtHhppMgl> findAll() throws ApplicationException {
        List<TipoOtHhppMgl> resultList;
        TipoOtHhppMglDaoImpl tipoOtHhppMglDaoImpl = new TipoOtHhppMglDaoImpl();
        resultList = tipoOtHhppMglDaoImpl.findAll();
        return resultList;
    }

    public TipoOtHhppMgl create(TipoOtHhppMgl tipoOtHhppMgl, String usuario, 
            int perfil) throws ApplicationException {
        TipoOtHhppMglDaoImpl tipoOtHhppMglDaoImpl = new TipoOtHhppMglDaoImpl();
        TipoOtHhppMgl tipoOtHhppMglReturn = tipoOtHhppMglDaoImpl.createCm(tipoOtHhppMgl,
                usuario, perfil);
        return tipoOtHhppMglReturn;
    }

    public TipoOtHhppMgl update(TipoOtHhppMgl tipoOtHhppMgl)
            throws ApplicationException {
        TipoOtHhppMglDaoImpl tipoOtHhppMglDaoImpl = new TipoOtHhppMglDaoImpl();
        return tipoOtHhppMglDaoImpl.update(tipoOtHhppMgl);
    }

    public boolean eliminarTipoOtHhpp(TipoOtHhppMgl tipoOtHhppMgl,String mUser, int mPerfil)
            throws ApplicationException {
        TipoOtHhppMglDaoImpl tipoOtHhppMglDaoImpl = new TipoOtHhppMglDaoImpl();
        return tipoOtHhppMglDaoImpl.deleteCm(tipoOtHhppMgl,mUser,mPerfil);
    }

    public TipoOtHhppMgl findById(TipoOtHhppMgl tipoOtHhppMgl)
            throws ApplicationException {
        TipoOtHhppMglDaoImpl tipoOtHhppMglDaoImpl = new TipoOtHhppMglDaoImpl();
        return tipoOtHhppMglDaoImpl.find(tipoOtHhppMgl.getTipoOtId());
    }

    /**
     * Lista los diferentes tipos de ot por el ID.
     *
     * @author Juan David Hernandez
     * @param tipoOtId
     * @return
     * @throws ApplicationException
     */
    public TipoOtHhppMgl findTipoOtHhppById(BigDecimal tipoOtId)
            throws ApplicationException {
        TipoOtHhppMglDaoImpl tipoOtHhppMglDaoImpl = new TipoOtHhppMglDaoImpl();
        return tipoOtHhppMglDaoImpl.findTipoOtHhppById(tipoOtId);
    }
    
        /**
     * Obtiene el conteo de todos los registros de tipo de ot creadas en la DB.
     *
     * @author Juan David Hernandez     
     * @return
     * @throws ApplicationException
     */
    public int countAllTipoOtHhpp() throws ApplicationException {
        int result;
        TipoOtHhppMglDaoImpl tipoOtHhppMglDaoImpl = new TipoOtHhppMglDaoImpl();
        result = tipoOtHhppMglDaoImpl.countAllTipoOtHhpp();
        return result;
    }
        
           /**
     * Lista los diferentes tipos de ot por el ID.
     *
     * @author Juan David Hernandez
     * @param paginaSelected
     * @param tipoOtId
     * @param maxResults
     * @return
     * @throws ApplicationException
     */ 
    public List<TipoOtHhppMgl> findAllTipoOtHhppPaginada(int paginaSelected,
            int maxResults) throws ApplicationException {
        List<TipoOtHhppMgl> resultList;
        
        TipoOtHhppMglDaoImpl tipoOtHhppMglDaoImpl = new TipoOtHhppMglDaoImpl();
        
        int firstResult = 0;
        if (paginaSelected > 1) {
            firstResult = (maxResults * (paginaSelected - 1));
        }
        resultList = tipoOtHhppMglDaoImpl.findAllTipoOtHhppPaginada(firstResult,
                maxResults);
        return resultList;
    }
    
     /**
     * Tipo de ot por el Nombre
     *
     * @author Juan David Hernandez
     * @param nombreTipoOtId nombre del tipo de ot
     * @return
     * @throws ApplicationException
     */
    public TipoOtHhppMgl findTipoOtHhppByNombreTipoOt(String nombreTipoOtId)
            throws ApplicationException {
        TipoOtHhppMglDaoImpl tipoOtHhppMglDaoImpl = new TipoOtHhppMglDaoImpl();
        return tipoOtHhppMglDaoImpl.findTipoOtHhppByNombreTipoOt(nombreTipoOtId);
    }
    
       /**
     * Validar que un tipo de ot no este en uso al estar asociada a una ot
     *
     * @author Juan David Hernandez
     * @param idTipoOt
     * @return verdadero si se encuentra en uso
     * @throws ApplicationException
     */
        public boolean validarTipoOtHhppEnUso(BigDecimal idTipoOt)
            throws ApplicationException {
        OtHhppMglDaoImpl otHhppMglDaoImpl = new OtHhppMglDaoImpl();
        return otHhppMglDaoImpl.validarTipoOtHhppEnUso(idTipoOt);
    }
        
        /**
     * Busca Tipo de Ot por Abreviatura. Permite buscar un tipo de OT en la base de
     * datos por la Abreviatura
     *
     * @author ortizjaf
     * @param abreviatura abreviatura para buscar el tipo de ot
     * @return tipo de ot encontrada por la abreviatura
     * @throws ApplicationException
     */
    public TipoOtHhppMgl findTipoOtByAbreviatura(String abreviatura)
            throws ApplicationException {
        TipoOtHhppMglDaoImpl tipoOtDao = new TipoOtHhppMglDaoImpl();
        return tipoOtDao.findTipoOtByIdentificadorInterno(abreviatura);
    }
}
