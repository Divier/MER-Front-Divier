/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtCompetenciaMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.cm.CmtCompetenciaAuditoriaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCompetenciaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.utils.UtilsCMAuditoria;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Manager Competencia. Contiene la logica de negocio de competencias en el
 * repositorio.
 *
 * @author Johnnatan Ortiz
 * @version 1.00.000
 */
public class CmtCompetenciaMglManager {

    public List<CmtCompetenciaMgl> findAll() throws ApplicationException {
        List<CmtCompetenciaMgl> resulList;
        CmtCompetenciaMglDaoImpl cmtCompetenciaMglDaoImpl = new CmtCompetenciaMglDaoImpl();
        resulList = cmtCompetenciaMglDaoImpl.findAll();
        return resulList;
    }

    public List<CmtCompetenciaMgl> findCompetenciaId(BigDecimal subEdificiosId) throws ApplicationException {
        CmtCompetenciaMglDaoImpl cmtCompetenciaMglDaoImpl = new CmtCompetenciaMglDaoImpl();
        return cmtCompetenciaMglDaoImpl.findCompetenciaId(subEdificiosId);
    }

    public CmtCompetenciaMgl create(CmtCompetenciaMgl cmtCompetenciaMgl,
            String usuario, int perfil)
            throws ApplicationException {

        List<CmtCompetenciaMgl> competenciaSubEdificioList = 
                cmtCompetenciaMgl.getSubEdificioObj().getCompetenciaList();
        
        for (CmtCompetenciaMgl ccm : competenciaSubEdificioList) {
            if (ccm.getCompetenciaTipo().getCodigoRr().equalsIgnoreCase(
                    cmtCompetenciaMgl.getCompetenciaTipo().getCodigoRr()) 
                && ccm.getEstadoRegistro() == 1) {
                throw new ApplicationException("El codigo de competencia ya esta asicado al edificio, no se puede agregar dos veces");
            }
        }
        
        CmtCuentaMatrizRRMglManager cmtCuentaMatrizRRMglManager = new CmtCuentaMatrizRRMglManager();
        boolean isActualizadoRr = cmtCuentaMatrizRRMglManager.crearCompetencia(cmtCompetenciaMgl, usuario, perfil);
        if (isActualizadoRr) {
            CmtCompetenciaMglDaoImpl cmtCompetenciaMglDaoImpl = new CmtCompetenciaMglDaoImpl();
            return cmtCompetenciaMglDaoImpl.createCm(cmtCompetenciaMgl, usuario, perfil);
        } else {
            throw new ApplicationException("No fue posible crear la competencia: 'Codigo: "
                    + cmtCompetenciaMgl.getCompetenciaTipo().getCodigoRr()
                    + ", Proveedor: "
                    + cmtCompetenciaMgl.getCompetenciaTipo().getProveedorCompetencia().getNombreBasica()
                    + ", Servicio: "
                    + cmtCompetenciaMgl.getCompetenciaTipo().getServicioCompetencia().getNombreBasica()
                    + "' en RR");
        }
    }

    public CmtCompetenciaMgl update(CmtCompetenciaMgl cmtCompetenciaMgl, String usuario, int perfil) throws ApplicationException {
        CmtCuentaMatrizRRMglManager cmtCuentaMatrizRRMglManager = new CmtCuentaMatrizRRMglManager();
        boolean isActualizadoRr = cmtCuentaMatrizRRMglManager.eliminarCompetencia(cmtCompetenciaMgl, usuario, perfil);
        if (isActualizadoRr) {
            CmtCompetenciaMglDaoImpl cmtCompetenciaMglDaoImpl = new CmtCompetenciaMglDaoImpl();
            return cmtCompetenciaMglDaoImpl.updateCm(cmtCompetenciaMgl, usuario, perfil);
        } else {
            throw new ApplicationException("No fue posible eliminar la competencia: 'Codigo: "
                    + cmtCompetenciaMgl.getCompetenciaTipo().getCodigoRr()
                    + ", Proveedor: "
                    + cmtCompetenciaMgl.getCompetenciaTipo().getProveedorCompetencia().getNombreBasica()
                    + ", Servicio: "
                    + cmtCompetenciaMgl.getCompetenciaTipo().getServicioCompetencia().getNombreBasica()
                    + "' en RR");
        }

    }

    public boolean delete(CmtCompetenciaMgl cmtCompetenciaMgl, String usuario, int perfil) throws ApplicationException {
        CmtCompetenciaMglDaoImpl cmtCompetenciaMglDaoImpl = new CmtCompetenciaMglDaoImpl();
        return cmtCompetenciaMglDaoImpl.deleteCm(cmtCompetenciaMgl, usuario, perfil);
    }

    public CmtCompetenciaMgl findById(CmtCompetenciaMgl cmtCompetenciaMgl) throws ApplicationException {
        CmtCompetenciaMglDaoImpl cmtCompetenciaMglDaoImpl = new CmtCompetenciaMglDaoImpl();
        return cmtCompetenciaMglDaoImpl.find(cmtCompetenciaMgl.getCompetenciaId());
    }

    public CmtCompetenciaMgl findById(BigDecimal id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Busca las Competencias por SubEdificio. Permite realizar la busqueda de
     * las Compentencias de un mismo SubEdificio.
     *
     * @author Johnnatan Ortiz
     * @param subEdificio SubEdificio
     * @return Compentencias asociadas a un SubEdifico
     * @throws ApplicationException
     */
    public List<CmtCompetenciaMgl> findBySubEdificio(CmtSubEdificioMgl subEdificio) throws ApplicationException {
        CmtCompetenciaMglDaoImpl daoImpl = new CmtCompetenciaMglDaoImpl();
        return daoImpl.findBySubEdificio(subEdificio);
    }

    /**
     * Cuenta las Competencias asociadas a un SubEdificio. Permite realizar el
     * conteo de las Compentencias de un mismo SubEdificio.
     *
     * @author Johnnatan Ortiz
     * @param subEdificio SubEdificio
     * @return Compentencias asociadas a un SubEdifico
     * @throws ApplicationException
     */
    public int getCountBySubEdificio(CmtSubEdificioMgl subEdificio) throws ApplicationException {
        CmtCompetenciaMglDaoImpl daoImpl = new CmtCompetenciaMglDaoImpl();
        return daoImpl.getCountBySubEdificio(subEdificio);
    }

    /**
     * Busca las Competencias por SubEdificio. Permite realizar la busqueda de
     * las Compentencias de un mismo SubEdificio paginando el resultado.
     *
     * @author Johnnatan Ortiz
     * @param paginaSelected pagina de la busqueda
     * @param maxResults maximo numero de resultados
     * @param subEdificio SubEdificio
     * @return Compentencias asociadas a un SubEdifico
     * @throws ApplicationException
     */
    public List<CmtCompetenciaMgl> findBySubEdificioPaginacion(int paginaSelected,
            int maxResults, CmtSubEdificioMgl subEdificio) throws ApplicationException {

        CmtCompetenciaMglDaoImpl daoImpl = new CmtCompetenciaMglDaoImpl();
        int firstResult = 0;
        if (paginaSelected > 0) {
            firstResult = (maxResults * (paginaSelected - 1));
        }
        return daoImpl.findBySubEdificioPaginado(firstResult, maxResults, subEdificio);
    }

    /**
     * Metodo para construir la Auditoria de Competencias
     *
     * @autor Julie Sarmiento
     * @param cmtCompetenciaMgl
     * @return Auditoria Competencias
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public List<AuditoriaDto> construirAuditoria(CmtCompetenciaMgl cmtCompetenciaMgl)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        UtilsCMAuditoria<CmtCompetenciaMgl, CmtCompetenciaAuditoriaMgl> utilsCMAuditoria = new UtilsCMAuditoria<CmtCompetenciaMgl, CmtCompetenciaAuditoriaMgl>();
        List<AuditoriaDto> listAuditoriaDto = utilsCMAuditoria.construirAuditoria(cmtCompetenciaMgl);
        return listAuditoriaDto;

    }
}
