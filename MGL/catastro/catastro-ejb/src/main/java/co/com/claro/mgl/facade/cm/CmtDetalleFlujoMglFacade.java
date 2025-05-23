/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtDetalleFlujoMglManager;
import co.com.claro.mgl.dtos.FiltroConsultaDetalleFlujoDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDetalleFlujoMgl;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Stateless;

/**
 * Facade Detalle Flujo de un tipo de Orden de Trabajo. Expone la logica de
 * negocio del detalle del flujo de un tipo de ordenes de trabajo en el
 * repositorio.
 *
 * @author Johnnatan Ortiz
 * @version 1.00.000
 */
@Stateless
public class CmtDetalleFlujoMglFacade implements CmtDetalleFlujoMglFacadeLocal {
    
    private String user = "";
    private int perfil = 0;
    /**
     * Busca el datalle del Flujo por el tipo del Flujo.Permite realizar la
 busqueda del detalle del Flujo por medio del tipo de Flujo en el
 repositorio.
     *
     * @author Johnnatan Ortiz
     * @param tipoFlujoOt orden de trabajo
     * @param tecnologia
     * @return lista del detalle del flujo
     * @throws ApplicationException
     */
    @Override
    public List<CmtDetalleFlujoMgl> findByTipoFlujo(CmtBasicaMgl tipoFlujoOt,CmtBasicaMgl tecnologia) throws ApplicationException {
        CmtDetalleFlujoMglManager manager = new CmtDetalleFlujoMglManager();
        return manager.findByTipoFlujo(tipoFlujoOt,tecnologia);
    }

    /**
     * Busca el datalle del Flujo por el tipo del Flujo y el estado Inicial.Permite realizar la busqueda del detalle del Flujo por medio del tipo de
 Flujo y el estado Inicial en el repositorio.
     *
     * @author Johnnatan Ortiz
     * @param tipoFlujoOt orden de trabajo
     * @param estadoInicial orden de trabajo
     * @param tecnologia
     * @return lista del detalle del flujo
     * @throws ApplicationException
     */
    @Override
    public List<CmtDetalleFlujoMgl> findByTipoFlujoAndEstadoIni(CmtBasicaMgl tipoFlujoOt, CmtBasicaMgl estadoInicial,CmtBasicaMgl tecnologia) throws ApplicationException {
        CmtDetalleFlujoMglManager manager = new CmtDetalleFlujoMglManager();
        return manager.findByTipoFlujoAndEstadoIni(tipoFlujoOt, estadoInicial,tecnologia);
    }

    @Override
    public List<CmtDetalleFlujoMgl> findAll() throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CmtDetalleFlujoMgl create(CmtDetalleFlujoMgl t) throws ApplicationException {
        CmtDetalleFlujoMglManager manager = new CmtDetalleFlujoMglManager();
        return manager.create(t, user, perfil);
    }

    @Override
    public CmtDetalleFlujoMgl update(CmtDetalleFlujoMgl t) throws ApplicationException {
        CmtDetalleFlujoMglManager manager = new CmtDetalleFlujoMglManager();
        return manager.update(t, user, perfil);
    }

    @Override
    public boolean delete(CmtDetalleFlujoMgl t) throws ApplicationException {
        CmtDetalleFlujoMglManager manager = new CmtDetalleFlujoMglManager();
        return manager.delete(t, user, perfil);
    }

    @Override
    public CmtDetalleFlujoMgl findById(CmtDetalleFlujoMgl sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FiltroConsultaDetalleFlujoDto findTablasDetalleFlujo(HashMap<String, Object> params, boolean contar, int firstResult, int maxResults) throws ApplicationException {
        CmtDetalleFlujoMglManager manager = new CmtDetalleFlujoMglManager();
        return manager.findTablasEstadoxFlujo(params, contar,firstResult,maxResults);
    }

    @Override
    public void setUser(String mUser, int mPerfil) throws ApplicationException {
        if (mUser.equals("") || mPerfil == 0) {
            throw new ApplicationException("El Usuario o perfil Nopueden ser nulos");
        }
        user = mUser;
        perfil = mPerfil;
    }
    
         
    
    /**
     * Busca una lista de  detalle flujo por identificador interno de la app
     *
     * @author Victor Bocanegra
     * @param codigoInternoApp codigo interno de la aplicacion
     * @return List<CmtDetalleFlujoMgl> lista encontrada
     * @throws ApplicationException
     */
    @Override
    public List<CmtDetalleFlujoMgl> findByCodigoInternoApp(String codigoInternoApp) throws ApplicationException {
        CmtDetalleFlujoMglManager manager = new CmtDetalleFlujoMglManager();
        return manager.findByCodigoInternoApp(codigoInternoApp);

    }

    @Override
    public List<CmtDetalleFlujoMgl> findByAllFields(HashMap<String, Object> params) throws ApplicationException {
        CmtDetalleFlujoMglManager manager = new CmtDetalleFlujoMglManager();
        return manager.findByAllFields(params);
    }
    
    /**
     * Busca el datalle del Flujo por el tipo del Flujo, estado Proximo,
     * tecnologia que tengan estado razonado.
     *
     * @author Victor Bocanegra
     * @param tipoFlujoOt orden de trabajo
     * @param proximoEstado orden de trabajo
     * @param tecnologia
     * @return lista del detalle del flujo
     * @throws ApplicationException
     */
    @Override
    public List<CmtDetalleFlujoMgl> findByTipoFlujoAndEstadoProAndTecRazon(CmtBasicaMgl tipoFlujoOt,
            CmtBasicaMgl proximoEstado, CmtBasicaMgl tecnologia) throws ApplicationException {
        CmtDetalleFlujoMglManager manager = new CmtDetalleFlujoMglManager();
        return manager.findByTipoFlujoAndEstadoProAndTecRazon(tipoFlujoOt, proximoEstado, tecnologia);
    }
}
