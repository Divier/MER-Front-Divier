/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtBlackListMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.cm.CmtBlackListMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import javax.ejb.Stateless;

/**
 * Facade CmtBlackListMgl. Expone la logica de negocio de los BlackList en el
 * repositorio.
 *
 * @author Johnnatan Ortiz
 * @version 1.00.000
 */
@Stateless
public class CmtBlackListMglFacade implements CmtBlackListMglFacadeLocal{
    
    /**
     * Busca todos los BlackList. Permite realizar la busqueda de todos los
     * BlackList.
     *
     * @author Johnnatan Ortiz
     * @return Todos BlackList
     * @throws ApplicationException
     */
    @Override
    public List<CmtBlackListMgl> findAll() throws ApplicationException {
        CmtBlackListMglManager cmtBlackListMglManager = new CmtBlackListMglManager();
        return cmtBlackListMglManager.findAll();
    }
    
    /**
     * Busca los BlackList por SubEdificio. Permite realizar la busqueda de los
     * BlackList de un mismo SubEdificio.
     *
     * @author Johnnatan Ortiz
     * @param paginaSelected pagina de la busqueda
     * @param maxResults maximo numero de resultados
     * @param subEdificio SubEdificio
     * @return BlackList asociados a un SubEdifico
     * @throws ApplicationException
     */
    @Override
    public List<CmtBlackListMgl> findBySubEdificioPaginado(int paginaSelected, int maxResults, CmtSubEdificioMgl subEdificio) throws ApplicationException {
        CmtBlackListMglManager cmtBlackListMglManager = new CmtBlackListMglManager();
        return cmtBlackListMglManager.findBySubEdificioPaginado(paginaSelected, maxResults, subEdificio);
    }
    
    /**
     * Cuenta los BlackList por SubEdificio. Permite realizar el conteo de los
     * BlackList de un mismo SubEdificio.
     *
     * @author Johnnatan Ortiz
     * @param subEdificio SubEdificio
     * @return numero de BlackList asociados a un SubEdifico
     * @throws ApplicationException
     */
    @Override
    public int getCountBySubEdificio(CmtSubEdificioMgl subEdificio) throws ApplicationException {
        CmtBlackListMglManager cmtBlackListMglManager = new CmtBlackListMglManager();
        return cmtBlackListMglManager.getCountBySubEdificio(subEdificio);
    }
    
    /**
     * Crea BlackList. Permite crear un Blacklist
     *
     * @author Johnnatan Ortiz
     * @param cmtBlackListMgl cmtBlackListMgl a crear
     * @param usuario usuario creacion
     * @param perfil perfil creacion
     * @return BlackList creado
     * @throws ApplicationException
     */
    @Override
    public CmtBlackListMgl create(CmtBlackListMgl cmtBlackListMgl, String usuario, int perfil) throws ApplicationException {
        CmtBlackListMglManager cmtBlackListMglManager = new CmtBlackListMglManager();
        return cmtBlackListMglManager.create(cmtBlackListMgl, usuario, perfil);
    }
    
    /**
     * Actualiza BlackList. Permite actualizar un Blacklist
     *
     * @author Johnnatan Ortiz
     * @param cmtBlackListMgl cmtBlackListMgl a actualizar
     * @param usuario usuario actualizacion
     * @param perfil perfil actualizacion
     * @return BlackList actualizado
     * @throws ApplicationException
     */
    @Override
    public CmtBlackListMgl update(CmtBlackListMgl cmtBlackListMgl, String usuario, int perfil) throws ApplicationException {
        CmtBlackListMglManager cmtBlackListMglManager = new CmtBlackListMglManager();
        return cmtBlackListMglManager.update(cmtBlackListMgl, usuario, perfil);
    }

    @Override
    public List<AuditoriaDto> construirAuditoria(CmtBlackListMgl cmtBlackListMgl)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (cmtBlackListMgl != null) {
            CmtBlackListMglManager cmtBlackListMglManager = new CmtBlackListMglManager();
            return cmtBlackListMglManager.construirAuditoria(cmtBlackListMgl);
        } else {
            return null;
        }
    }
    @Override
      public List<CmtBlackListMgl> findBySubEdificio(CmtSubEdificioMgl subEdificio) throws ApplicationException {
        CmtBlackListMglManager cmtBlackListMglManager = new CmtBlackListMglManager();
        return cmtBlackListMglManager.findBySubEdificio(subEdificio);
    }
    
    
}
