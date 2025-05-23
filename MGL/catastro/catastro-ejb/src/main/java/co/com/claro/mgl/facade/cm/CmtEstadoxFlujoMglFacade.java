/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtEstadoxFlujoMglManager;
import co.com.claro.mgl.dtos.FiltroConsultaEstadosxFlujoDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtEstadoxFlujoMgl;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Stateless;

/**
 * Facade Estados del Flujo de un tipo de Orden de Trabajo. Expone la logica de
 * negocio de los estados del flujo de un tipo de ordenes de trabajo en el
 * repositorio.
 *
 * @author Johnnatan Ortiz
 * @version 1.00.000
 */
@Stateless
public class CmtEstadoxFlujoMglFacade implements CmtEstadoxFlujoMglFacadeLocal {

    private String user;
    private int perfil;

    /**
     * Obtiene el estado inicial del flujo.Permite obtener el estado incial del
     * flujo.
     *
     * @author Johnnatan Ortiz
     * @param tipoFlujoOt Tipo de Flujo
     * @param tegnologia
     * @return Estado Inicial del Tipo de Flujo
     * @throws ApplicationException
     */
    @Override
    public CmtEstadoxFlujoMgl getEstadoInicialFlujo(CmtBasicaMgl tipoFlujoOt, CmtBasicaMgl tegnologia) throws ApplicationException {
        CmtEstadoxFlujoMglManager manager = new CmtEstadoxFlujoMglManager();
        return manager.getEstadoInicialFlujo(tipoFlujoOt, tegnologia);
    }

    /**
     * Obtiene los estados de un tipo de flujo.Permite obtener los estados de un
     * tipo de flujo.
     *
     * @author Johnnatan Ortiz
     * @param tipoFlujoOt Tipo de Flujo
     * @param tegnologia
     * @return Estados del Tipo de Flujo
     * @throws ApplicationException
     */
    @Override
    public List<CmtEstadoxFlujoMgl> getEstadosByTipoFlujo(CmtBasicaMgl tipoFlujoOt, CmtBasicaMgl tegnologia) throws ApplicationException {
        CmtEstadoxFlujoMglManager manager = new CmtEstadoxFlujoMglManager();
        return manager.getEstadosByTipoFlujo(tipoFlujoOt, tegnologia);
    }

    @Override
    public FiltroConsultaEstadosxFlujoDto findTablasEstadoxFlujo(HashMap<String, Object> params, boolean contar, int firstResult, int maxResults) throws ApplicationException {
        CmtEstadoxFlujoMglManager manager = new CmtEstadoxFlujoMglManager();
        return manager.findTablasEstadoxFlujo(params, contar, firstResult, maxResults);
    }

    @Override
    public CmtEstadoxFlujoMgl create(CmtEstadoxFlujoMgl t, String usuario, int perfilUsu) throws ApplicationException {
        CmtEstadoxFlujoMglManager cmtEstadoxFlujoMglManager = new CmtEstadoxFlujoMglManager();
        return cmtEstadoxFlujoMglManager.create(t, usuario, perfilUsu);
    }

    @Override
    public CmtEstadoxFlujoMgl update(CmtEstadoxFlujoMgl t, String usuario, int perfilUsu) throws ApplicationException {
        CmtEstadoxFlujoMglManager cmtEstadoxFlujoMglManager = new CmtEstadoxFlujoMglManager();
        return cmtEstadoxFlujoMglManager.update(t, usuario, perfilUsu);
    }

    @Override
    public boolean delete(CmtEstadoxFlujoMgl t, String usuario, int perfilUsu) throws ApplicationException {
        CmtEstadoxFlujoMglManager cmtEstadoxFlujoMglManager = new CmtEstadoxFlujoMglManager();
        return cmtEstadoxFlujoMglManager.delete(t, usuario, perfilUsu);
    }

    @Override
    public CmtEstadoxFlujoMgl findById(BigDecimal id) throws ApplicationException {
        CmtEstadoxFlujoMglManager cmtEstadoxFlujoMglManager = new CmtEstadoxFlujoMglManager();
        return cmtEstadoxFlujoMglManager.findById(id);
    }

    @Override
    public boolean finalizoEstadosxFlujoDto(CmtBasicaMgl tipoFlujoOt, CmtBasicaMgl estadoInterno, CmtBasicaMgl tegnologia) throws ApplicationException {
        CmtEstadoxFlujoMglManager cmtEstadoxFlujoMglManager = new CmtEstadoxFlujoMglManager();
        return cmtEstadoxFlujoMglManager.finalizoEstadosxFlujoDto(tipoFlujoOt, estadoInterno, tegnologia);
    }

    @Override
    public void setUser(String mUser, int mPerfil) throws ApplicationException {
        this.user = mUser;
        this.perfil = mPerfil;
    }

    /**
     * Obtiene un estado por tipo de flujo y el estado.Permite obtener el
     * detalle de un estado dentro de los estados de un Tipo de Flujo, filtrando
     * por el Tipo de Flujo y el estado.
     *
     * @author Victor Bocanegra
     * @param tipoFlujoOt Tipo de Flujo
     * @param estadoInterno Estado
     * @param tegnologia
     * @return Estado del Tipo de Flujo
     * @throws ApplicationException
     */
    @Override
    public CmtEstadoxFlujoMgl findByTipoFlujoAndEstadoInt(CmtBasicaMgl tipoFlujoOt,
            CmtBasicaMgl estadoInterno, CmtBasicaMgl tegnologia) throws ApplicationException {
        CmtEstadoxFlujoMglManager cmtEstadoxFlujoMglManager = new CmtEstadoxFlujoMglManager();
        return cmtEstadoxFlujoMglManager.findByTipoFlujoAndEstadoInt(tipoFlujoOt, estadoInterno, tegnologia);
    }

    @Override
    public List<CmtEstadoxFlujoMgl> findByAllFields(HashMap<String, Object> params) throws ApplicationException {
        CmtEstadoxFlujoMglManager cmtEstadoxFlujoMglManager = new CmtEstadoxFlujoMglManager();

        return cmtEstadoxFlujoMglManager.findByAllFields(params);
    }

    /**
     * Obtiene los estados del tipo de flujo ot Vt con estados internos de
     * cierre.
     *
     * @author Victor Bocanegra Ortiz
     * @param tipoFlujoOt Tipo de Flujo
     * @param tegnologia
     * @param estadosInternosCerrarOt estados de cierre comercial
     * @return List<CmtEstadoxFlujoMgl>
     * @throws ApplicationException
     */
    @Override
    public List<CmtEstadoxFlujoMgl> getEstadosByTipoFlujoAndTecAndCieCom(CmtBasicaMgl tipoFlujoOt,
            CmtBasicaMgl tegnologia, List<BigDecimal> estadosInternosCerrarOt)
            throws ApplicationException {

        CmtEstadoxFlujoMglManager cmtEstadoxFlujoMglManager = new CmtEstadoxFlujoMglManager();
        return cmtEstadoxFlujoMglManager.getEstadosByTipoFlujoAndTecAndCieCom(tipoFlujoOt, tegnologia, estadosInternosCerrarOt);

    }

    /**
     * Obtiene un estado de un tipo de FLujo con estado inicial 'C'
     *
     * @author Victor Bocanegra
     * @param tipoFlujoOt Tipo de Flujo
     * @param tegnologia tipo tecnologia
     * @return Estado del Tipo de Flujo
     * @throws ApplicationException
     */
    @Override
    public CmtEstadoxFlujoMgl findByTipoFlujoAndTecnoAndCancelado(CmtBasicaMgl tipoFlujoOt,
            CmtBasicaMgl tegnologia) throws ApplicationException {

        CmtEstadoxFlujoMglManager cmtEstadoxFlujoMglManager = new CmtEstadoxFlujoMglManager();
        return cmtEstadoxFlujoMglManager.findByTipoFlujoAndTecnoAndCancelado(tipoFlujoOt, tegnologia);

    }
}
