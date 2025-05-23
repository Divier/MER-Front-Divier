/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtTipoOtMglManager;
import co.com.claro.mgl.dtos.FiltroConsultaSlaOtDto;
import co.com.claro.mgl.dtos.FiltroConsultaSubTipoDto;
import co.com.claro.mgl.dtos.TipoOtCmDirDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoOtMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 * Facade Tipo de Orden de Trabajo. Expone la logica de negocio para el manejo
 * de los tipos de ordenes de trabajo en el repositorio.
 *
 * @author Johnnatan Ortiz
 * @version 1.00.000
 */
@Stateless
public class CmtTipoOtMglFacade implements CmtTipoOtMglFacadeLocal {

    private String user = "";
    private int perfil = 0;

    /**
     * Obtiene todos los Tipo de Ordenes de Trabajo. Permite obtener todos los
     * tipos de Ordenes de Trabajo que existen en el repositorio.
     *
     * @author Johnnatan Ortiz
     * @return Tipos de Ordenes de Trabajo
     * @throws ApplicationException
     */
    @Override
    public List<CmtTipoOtMgl> findAll() throws ApplicationException {
        CmtTipoOtMglManager manager = new CmtTipoOtMglManager();
        return manager.findAll();
    }

    /**
     * Obtiene un Tipo de Ordenes de Trabajo por su ID. Permite obtener un tipo
     * de Ordene de Trabajo que existen en el repositorio por el ID.
     *
     * @author Johnnatan Ortiz
     * @param idTipoOt Id del tipo de Orden de trabajo
     * @return Tipo de Ordene de Trabajo
     * @throws ApplicationException
     */
    @Override
    public CmtTipoOtMgl findById(BigDecimal idTipoOt) throws ApplicationException {
        CmtTipoOtMglManager manager = new CmtTipoOtMglManager();
        return manager.findById(idTipoOt);
    }

    @Override
    public CmtTipoOtMgl create(CmtTipoOtMgl t) throws ApplicationException {
        CmtTipoOtMglManager manager = new CmtTipoOtMglManager();
        return manager.create(t);
    }

    @Override
    public CmtTipoOtMgl update(CmtTipoOtMgl t) throws ApplicationException {
        CmtTipoOtMglManager manager = new CmtTipoOtMglManager();
        return manager.update(t);
    }

    @Override
    public boolean delete(CmtTipoOtMgl t) throws ApplicationException {
        CmtTipoOtMglManager manager = new CmtTipoOtMglManager();
        return manager.delete(t);
    }

    @Override
    public CmtTipoOtMgl findById(CmtTipoOtMgl sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setUser(String mUser, int mPerfil) throws ApplicationException {
        if (mUser.isEmpty() || mPerfil == 0) {
            throw new ApplicationException("El Usuario o perfil Nopueden ser nulos");
        }
        user = mUser;
        perfil = mPerfil;
    }

    @Override
    public FiltroConsultaSlaOtDto findListTablaSlaOt(FiltroConsultaSubTipoDto filtro, boolean contar, int firstResult, int maxResults) throws ApplicationException {
        CmtTipoOtMglManager manager = new CmtTipoOtMglManager();
        return manager.findListTablaSlaOt(filtro, contar, firstResult, maxResults);
    }

    @Override
    public List<CmtTipoOtMgl> findByBasicaId(CmtBasicaMgl cmtBasicaMgl) throws ApplicationException {
        CmtTipoOtMglManager manager = new CmtTipoOtMglManager();
        return manager.findByBasicaId(cmtBasicaMgl);
    }

    /**
     * Busca el subtipo de ot por identificador interno de la app
     *
     * @author Victor Bocanegra
     * @param codigoInternoApp codigo interno de la tabla
     * @return CmtTipoOtMgl encontrado
     * @throws ApplicationException
     */
    @Override
    public CmtTipoOtMgl findByCodigoInternoApp(String codigoInternoApp) throws ApplicationException {
        CmtTipoOtMglManager manager = new CmtTipoOtMglManager();
        return manager.findByCodigoInternoApp(codigoInternoApp);
    }
    
    /**
     * Busca los subtipo de ot escluyendo los de acometida u otro
     *
     * @author Victor Bocanegra
     * @param idAco id de acometidas u otro
     * @return List<CmtTipoOtMgl> encontrado
     * @throws ApplicationException
     */
    @Override
    public List<CmtTipoOtMgl> findByNoIdAco(List<BigDecimal> idAco) throws ApplicationException {
        CmtTipoOtMglManager manager = new CmtTipoOtMglManager();
        return manager.findByNoIdAco(idAco);

    }
    
    /**
     * Obtiene todos los Tipo de Ordenes de Trabajo que generan OT de acometidas
     * que existen en el repositorio.
     *
     * @param tipoOtAcometida tipo OT acometida
     * @author Victor Bocanegra
     * @return List<CmtTipoOtMgl> Tipos de Ordenes de Trabajo
     * @throws ApplicationException
     */
    @Override
    public List<CmtTipoOtMgl> findAllTipoOtGeneraAco(CmtTipoOtMgl tipoOtAcometida)
            throws ApplicationException {
        CmtTipoOtMglManager manager = new CmtTipoOtMglManager();
        return manager.findAllTipoOtGeneraAco(tipoOtAcometida);
        
    }
    
    /**
     * Obtiene todos los Sub Tipos de Ordenes de Trabajo que generan acometidas
     * en el repositorio.
     *
     * @author Victor Bocanegra
     * @return List<CmtTipoOtMgl> Sub Tipos de Ordenes de Trabajo
     * @throws ApplicationException
     */
    @Override
    public List<CmtTipoOtMgl> findAllTipoOtAcometidas()
            throws ApplicationException {

        CmtTipoOtMglManager manager = new CmtTipoOtMglManager();
        return manager.findAllTipoOtAcometidas();
    }
    
    /**
     * Obtiene todos los Sub Tipos de Ordenes de Trabajo de un tipode trabajo
     * que no son acometidas son VTs en el repositorio.
     *
     * @author Victor Bocanegra
     * @param cmtBasicaMgl Tipo de trabajo
     * @return List<CmtTipoOtMgl> Sub Tipos de Ordenes de Trabajo
     * @throws ApplicationException
     */
    @Override
    public List<CmtTipoOtMgl> findByTipoTrabajoAndIsVT(CmtBasicaMgl cmtBasicaMgl)
            throws ApplicationException {

        CmtTipoOtMglManager manager = new CmtTipoOtMglManager();
        return manager.findByTipoTrabajoAndIsVT(cmtBasicaMgl);
    }
    
    /**
     * Obtiene todos los Sub Tipos de Ordenes de Trabajo Vts que generan
     * acometidas en el repositorio.
     *
     * @author Victor Bocanegra
     * @return List<CmtTipoOtMgl> Sub Tipos de Ordenes de Trabajo
     * @throws ApplicationException
     */
    @Override
    public List<CmtTipoOtMgl> findAllTipoOtVts()
            throws ApplicationException {

        CmtTipoOtMglManager manager = new CmtTipoOtMglManager();
        return manager.findAllTipoOtVts();
    }

    @Override
    public List<TipoOtCmDirDto> findAllSubTipoOtCmHhpp() throws ApplicationException {
       CmtTipoOtMglManager manager = new CmtTipoOtMglManager();
        return manager.findAllSubTipoOtCmHhpp();
    }
     /**
     * Obtiene todos los Sub Tipo de Ordenes de Trabajo segun la tecnologia
     *
     * @author bocanegravm
     * @param basicaTecnologia
     * @return List<CmtTipoOtMgl> Tipos de Ordenes de Trabajo
     * @throws ApplicationException
     */
    @Override
    public List<CmtTipoOtMgl> findSubTipoOtByTecno(CmtBasicaMgl basicaTecnologia)
            throws ApplicationException {

        CmtTipoOtMglManager manager = new CmtTipoOtMglManager();
        return manager.findSubTipoOtByTecno(basicaTecnologia);
    }
}
