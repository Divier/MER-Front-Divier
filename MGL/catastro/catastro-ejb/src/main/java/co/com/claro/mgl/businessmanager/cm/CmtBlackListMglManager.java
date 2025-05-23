/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.businessmanager.address.ParametrosMglManager;
import co.com.claro.mgl.dao.impl.cm.CmtBlackListMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBlackListAuditoriaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBlackListMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.UtilsCMAuditoria;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Manager CmtBlackListMgl. Contiene la logica de negocio de los BlackList en el
 * repositorio.
 *
 * @author Johnnatan Ortiz
 * @version 1.00.000
 */
public class CmtBlackListMglManager {

    /**
     * Busca todos los BlackList. Permite realizar la busqueda de todos los
     * BlackList.
     *
     * @author Johnnatan Ortiz
     * @return Todos BlackList
     * @throws ApplicationException
     */
    public List<CmtBlackListMgl> findAll() throws ApplicationException {
        CmtBlackListMglDaoImpl cmtBlackListMglDaoImpl = new CmtBlackListMglDaoImpl();
        return cmtBlackListMglDaoImpl.findAll();
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
    public List<CmtBlackListMgl> findBySubEdificioPaginado(int paginaSelected,
            int maxResults, CmtSubEdificioMgl subEdificio) throws ApplicationException {
        CmtBlackListMglDaoImpl cmtBlackListMglDaoImpl = new CmtBlackListMglDaoImpl();
        int firstResult = 0;
        if (paginaSelected > 1) {
            firstResult = (maxResults * (paginaSelected - 1));
        }
        return cmtBlackListMglDaoImpl.findBySubEdificioPaginado(firstResult, maxResults, subEdificio);
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
    public int getCountBySubEdificio(CmtSubEdificioMgl subEdificio) throws ApplicationException {
        CmtBlackListMglDaoImpl cmtBlackListMglDaoImpl = new CmtBlackListMglDaoImpl();
        return cmtBlackListMglDaoImpl.getCountBySubEdificio(subEdificio);
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
    public CmtBlackListMgl create(CmtBlackListMgl cmtBlackListMgl, String usuario, int perfil) throws ApplicationException {

        boolean habilitarRR = false;  
        boolean createBlackListCm = false;
        ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
        ParametrosMgl parametroHabilitarRR = parametrosMglManager.findParametroMgl(Constant.HABILITAR_RR);
        if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase("1")) {
            habilitarRR = true;
        }
        if (cmtBlackListMgl.getSubEdificioObj().getListCmtBlackListMgl() != null
                && !cmtBlackListMgl.getSubEdificioObj().getListCmtBlackListMgl().isEmpty()) {
            for (CmtBlackListMgl blackListMgl : cmtBlackListMgl.getSubEdificioObj().getListCmtBlackListMgl()) {
                if (blackListMgl.getBlackListObj().getBasicaId().compareTo(
                        cmtBlackListMgl.getBlackListObj().getBasicaId()) == 0
                        && blackListMgl.getBlackListObj().getEstadoRegistro() == 1) {
                    throw new ApplicationException(": Este Black list ya se encuentra asociado");
                }
            }
        }

        
        CmtBlackListMglDaoImpl cmtBlackListMglDaoImpl = new CmtBlackListMglDaoImpl();
        CmtCuentaMatrizRRMglManager rRMglManager = new CmtCuentaMatrizRRMglManager();
        if(habilitarRR){
             createBlackListCm = rRMglManager.createBlackListCm(cmtBlackListMgl, usuario);
        }else{
             return cmtBlackListMglDaoImpl.createCm(cmtBlackListMgl, usuario, perfil);
        }
        if (createBlackListCm) {
            return cmtBlackListMglDaoImpl.createCm(cmtBlackListMgl, usuario, perfil);
        } else {
            throw new ApplicationException("No fue posible crear el BlackList en RR");
        }
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
    public CmtBlackListMgl createWithAutRR(CmtBlackListMgl cmtBlackListMgl, String usuario, int perfil) throws ApplicationException {
        CmtBlackListMglDaoImpl cmtBlackListMglDaoImpl = new CmtBlackListMglDaoImpl();
        return cmtBlackListMglDaoImpl.createCm(cmtBlackListMgl, usuario, perfil);
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
    public CmtBlackListMgl update(CmtBlackListMgl cmtBlackListMgl, String usuario, int perfil) throws ApplicationException {
        CmtBlackListMglDaoImpl cmtBlackListMglDaoImpl = new CmtBlackListMglDaoImpl();
        CmtCuentaMatrizRRMglManager rRMglManager = new CmtCuentaMatrizRRMglManager();
        boolean updateBlackListCm = rRMglManager.updateBlackListCm(cmtBlackListMgl, usuario);
        if (updateBlackListCm) {
            return cmtBlackListMglDaoImpl.updateCm(cmtBlackListMgl, usuario, perfil);
        } else {
            throw new ApplicationException("No fue posible crear el BlackList en RR");
        }
    }

    /**
     * Metodo para construir la auditoria de Black List
     *
     * @autor Julie Sarmiento
     * @param cmtBlackListMgl
     * @return Auditoria Black List
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public List<AuditoriaDto> construirAuditoria(CmtBlackListMgl cmtBlackListMgl)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        UtilsCMAuditoria<CmtBlackListMgl, CmtBlackListAuditoriaMgl> utilsCMAuditoria = new UtilsCMAuditoria<CmtBlackListMgl, CmtBlackListAuditoriaMgl>();
        List<AuditoriaDto> listAuditoriaDto = utilsCMAuditoria.construirAuditoria(cmtBlackListMgl);
        return listAuditoriaDto;

    }
    
     public List<CmtBlackListMgl> findBySubEdificio(CmtSubEdificioMgl subEdificio) throws ApplicationException {
        CmtBlackListMglDaoImpl cmtBlackListMglDaoImpl = new CmtBlackListMglDaoImpl();
        return cmtBlackListMglDaoImpl.findBySubEdificio(subEdificio);
    }
     
    /**
     * Busca un BlackList por id
     *
     * @author Victor Bocanegra
     * @param idBlacklist
     * @return BlackList encontrado
     * @throws ApplicationException
     */
    public CmtBlackListMgl findById(BigDecimal idBlacklist) throws ApplicationException {

        CmtBlackListMglDaoImpl cmtBlackListMglDaoImpl = new CmtBlackListMglDaoImpl();
        return cmtBlackListMglDaoImpl.find(idBlacklist);

    }
    
    /**
     * Borrado logico de un BlackList
     *
     * @author Victor Bocanegra
     * @param blackListMgl
     * @param usuario
     * @param perfil
     * @return boolean
     * @throws ApplicationException
     */
    public boolean deleteCm(CmtBlackListMgl blackListMgl, String usuario, int perfil) throws ApplicationException {

        CmtBlackListMglDaoImpl cmtBlackListMglDaoImpl = new CmtBlackListMglDaoImpl();
        return cmtBlackListMglDaoImpl.deleteCm(blackListMgl, usuario, perfil);

    }
}
