/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtRunableMasivoCuentaMatrizManager;
import co.com.claro.mgl.businessmanager.cm.CmtRunableReporteCuentaMatrizManager;
import co.com.claro.mgl.businessmanager.cm.CmtTecnologiaSubMglManager;
import co.com.claro.mgl.dtos.CmtPestanaPenetracionDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTecnologiaSubMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtVisitaTecnicaMgl;
import co.com.claro.mgl.rest.dtos.MasivoModificacionDto;
import co.com.claro.mgl.utils.Constant;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observer;
import javax.ejb.Stateless;

/**
 *
 * @author rodriguezluim
 */
@Stateless
public class CmtTecnologiaSubMglFacade implements CmtTecnologiaSubMglFacadeLocal {

    private String user = "";
    private int perfil = 0;
    private CmtTecnologiaSubMglManager manager = new CmtTecnologiaSubMglManager();

    /**
     * Permite la actualizacion de los costos de la tabla sub tecnologia Sumando
     * todas las OT realizadas y guardandolo en el objeto TegnologiaSub de la
     * cuenta general de la cuenta matriz
     *
     * @author rodiguezluim
     * @param cuentaMatriz cuenta matriz para buscar las ordenes de trabajo que
     * la afectan.
     * @param cmtVisitaTecnicaMgl
     * @param tecnologia Tecnologia de las ordenes de trabajo la cual se quiere
     * calcular los costos
     * @throws ApplicationException
     */
    @Override
    public void actualizarCostosTecnologiaSubEdificioGeneral(CmtCuentaMatrizMgl cuentaMatriz, CmtVisitaTecnicaMgl cmtVisitaTecnicaMgl, CmtBasicaMgl tecnologia) throws ApplicationException {
        manager.actualizarCostosTecnologiaSubEdificioGeneral(cuentaMatriz, tecnologia, cmtVisitaTecnicaMgl, user, perfil);
    }

    /**
     * Permite la actualizacion de los costos de cada una de las sub tecnologias
     * afectadas por una orden de trabajo, y almacenadolas en su respectiba
     * tecnologia sub de acuerdo al edificio
     *
     * @author rodiguezluim
     * @param ordenTrabajo Orden de trabajo qque cambio de estado y se desean
     * actualizar los costos de las Tecnologias sub
     * @throws ApplicationException
     */
    @Override
    public void actualizarCostosSubEdificios(CmtOrdenTrabajoMgl ordenTrabajo) throws ApplicationException {
        manager.actualizarCostosSubEdificios(ordenTrabajo, user, perfil);
    }

    /**
     * Busca las tecnologias asociadas a los Subedificios y Cuenta Matriz
     *
     * @author cardenaslb
     * @throws ApplicationException
     */
    @Override
    public CmtPestanaPenetracionDto findListTecnologiasPenetracionSubCM(HashMap<String, Object> params, int page,
            int numeroRegistrosConsulta, CmtSubEdificioMgl cmtSubEdificioMgl, Constant.FIND_HHPP_BY fyndBy) throws ApplicationException {
        CmtTecnologiaSubMglManager cmtTecnologiaSubMglManager = new CmtTecnologiaSubMglManager();
        return cmtTecnologiaSubMglManager.findListTecnologiasPenetracionSubCM(params, page, numeroRegistrosConsulta, cmtSubEdificioMgl, fyndBy);
    }

    public List<CmtTecnologiaSubMgl> findAll() throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public CmtTecnologiaSubMgl create(CmtTecnologiaSubMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public CmtTecnologiaSubMgl update(CmtTecnologiaSubMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean delete(CmtTecnologiaSubMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public CmtTecnologiaSubMgl findById(CmtTecnologiaSubMgl sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Conteo de tecnologias en tabla TecnologiaSub por CM a una VT activa
     *
     * @author rodiguezluim
     * @throws ApplicationException
     */
    @Override
    public int countListPenetracionTecCM(HashMap<String, Object> params, CmtSubEdificioMgl cmtSubEdificioMgl, Constant.FIND_HHPP_BY fyndBy) throws ApplicationException {
        CmtTecnologiaSubMglManager cmtTecnologiaSubMglManager = new CmtTecnologiaSubMglManager();
        return cmtTecnologiaSubMglManager.countListPenetracionTecSub(params, cmtSubEdificioMgl, fyndBy);
    }

    /**
     * Actualizar el camopo meta de las tecnologis sub edificio correspondientes
     * a una VT activa
     *
     * @author rodiguezluim
     * @throws ApplicationException
     */
    @Override
    public int countListPenetracionTecSub(HashMap<String, Object> params, CmtSubEdificioMgl cmtSubEdificioMgl, Constant.FIND_HHPP_BY fyndBy) throws ApplicationException {
        CmtTecnologiaSubMglManager cmtTecnologiaSubMglManager = new CmtTecnologiaSubMglManager();
        return cmtTecnologiaSubMglManager.countListPenetracionTecSub(params, cmtSubEdificioMgl, fyndBy);
    }

    /**
     * Actualizar el camopo meta de las tecnologis sub edificio correspondientes
     * a una VT activa
     *
     * @author rodiguezluim
     * @param visitaTecnica
     * @throws ApplicationException
     */
    @Override
    public void guardarMetaSubEdificios(CmtVisitaTecnicaMgl visitaTecnica) throws ApplicationException {
        manager.guardarMetaSubEdificios(visitaTecnica, user, perfil);
    }

    /**
     * Actualizar el campo FECHA HABILITACION de las tecnologis sub edificio
     * correspondientes a una VT activa
     *
     * @author rodiguezluim
     * @param visitaTecnica
     * @throws ApplicationException
     */
    @Override
    public void guardarFechaHabilitacionSubEdificios(CmtVisitaTecnicaMgl visitaTecnica) throws ApplicationException {
        manager.guardarFechaHabilitacionSubEdificios(visitaTecnica, user, perfil);
    }

    /**
     * Actualizar el campo TIEMPO DE RECUPERACION de las tecnologis sub edificio
     * correspondientes a una VT activa
     *
     * @author rodiguezluim
     * @param visitaTecnica
     * @throws ApplicationException
     */
    @Override
    public void guardarTiempoRecuperacionSubEdificios(CmtVisitaTecnicaMgl visitaTecnica) throws ApplicationException {
        manager.guardarTiempoRecuperacionSubEdificios(visitaTecnica, user, perfil);
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
     * Busca las tecnologias asociadas a un subedificio.
     *
     * @author Victor Bocanegra
     * @param cmtSubEdificioMgl SubEdificio
     * @return Tecnologias asociadas a un Subedificio.
     * @throws ApplicationException
     */
    @Override
    public List<CmtTecnologiaSubMgl> findTecnoSubBySubEdi(
            CmtSubEdificioMgl cmtSubEdificioMgl) throws ApplicationException {

        return manager.findTecnoSubBySubEdi(cmtSubEdificioMgl);
    }
    
    /**
     * valbuenayf metodo para realizar el reporte de cuentamatriz para el cargue
     * masivo
     *
     * @param params
     * @param usuario
     * @param first
     * @param pageSize
     */
    @Override
    public void findReporteGeneralDetallado(Map<String, Object> params, String usuario) {
        Thread thread = new Thread(new CmtRunableReporteCuentaMatrizManager(params, usuario));
        thread.start();
    }

    /**
     * valbuenayf metodo para contar la cantidad de registros del reporte de
     * cuentamatriz para el cargue masivo
     *
     * @param params
     * @return
     */
    @Override
    public Integer findCountRepGeneralDetallado(Map<String, Object> params) {
        return manager.findCountRepGeneralDetallado(params);
    }

    /**
     * valbuenayf metodo para actualizar los atributos de cuenta matriz
     *
     * @param listaModificacion
     * @param usuario
     * @param tipoModificacion
     * @param nombreArchivo
     * @param perfil
     */
    @Override
    public void actualizarCargueMasivoCuentaMatriz(Observer observer, List<MasivoModificacionDto> listaModificacion, String usuario, Integer tipoModificacion, int perfil, String nombreArchivo) {
        Thread thread = new Thread(new CmtRunableMasivoCuentaMatrizManager(observer, listaModificacion, usuario, tipoModificacion, perfil, nombreArchivo));
        thread.start();
    }
    
    /**
     * Autor: Victor Bocanegra Metodo para la creacion las tecnologias
     * subedificio configuradas en la gestion de la creacion de una CM
     *
     * @param datosGestion
     * @param cmtSubEdificioMgl
     * @param usuario
     * @param perfil
     */
    @Override
    public void crearTecnSubXGestion(Map<CmtBasicaMgl, NodoMgl> datosGestion,
            CmtSubEdificioMgl cmtSubEdificioMgl, String usuario, int perfil) {

        manager.crearTecnSubXGestion(datosGestion, cmtSubEdificioMgl, usuario, perfil);
    }
    
    @Override
    public CmtTecnologiaSubMgl crearTecnSubXPenetracion(CmtSubEdificioMgl subEdificioMgl,
            NodoMgl nodoSeleccionado, CmtBasicaMgl basicaIdEstadosTec, String usuarioVT, int perfilVT) {
        return manager.crearTecnSubXPenetracion(subEdificioMgl,
                nodoSeleccionado, basicaIdEstadosTec, usuarioVT, perfilVT);
    }

    /**
     * Borra una tecnologia asociada a un subedificio
     *
     * @author Victor Bocanegra
     * @param cmtTecnologiaSubMgl
     * @param subEdificio Condicional por el cual voy a borrar
     * @param usuario
     * @param perfil
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @Override
    public void deleteSubEdificioTecnologia(CmtTecnologiaSubMgl cmtTecnologiaSubMgl, String usuario, int perfil)
            throws ApplicationException {
        manager.deleteSubEdificioTecnologia(cmtTecnologiaSubMgl, usuario, perfil);
    }
    
        /**
     * Busca las tecnologias asociadas a un subedificio por tecnologia.
     *
     * @author Victor Bocanegra
     * @param cmtSubEdificioMgl
     * @param cmtBasicaMgl
     * @return Tecnologias asociadas a un Subedificio.
     * @throws ApplicationException
     */
    @Override
    public List<CmtTecnologiaSubMgl> findTecnoSubBySubEdiTec(CmtSubEdificioMgl cmtSubEdificioMgl, CmtBasicaMgl cmtBasicaMgl)
            throws ApplicationException {
        
        return manager.findTecnoSubBySubEdiTec(cmtSubEdificioMgl, cmtBasicaMgl);
    }
    
    /**
     * Buscar una sub tecnologia basandose en un subedificio y la tecnologia
     *
     * @author rodriguezluim
     * @param cmtSubEdificioMgl
     * @param cmtBasicaMgl
     * @return Un elemento de tipo CmtTecnologiaSub
     */
    @Override
    public CmtTecnologiaSubMgl findBySubEdificioTecnologia(CmtSubEdificioMgl cmtSubEdificioMgl, CmtBasicaMgl cmtBasicaMgl) {

        return manager.findBySubEdificioTecnologia(cmtSubEdificioMgl, cmtBasicaMgl);
    }
    
    /**
     * Actualiza una tecnologia sub asociada a un subedificio
     *
     * @author Victor Bocanegra
     * @param cmtTecnologiaSubMgl objeto que se va actualizar
     * @param usuario
     * @param perfil
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException 
     */
    @Override
    public CmtTecnologiaSubMgl updateTecnoSub(CmtTecnologiaSubMgl cmtTecnologiaSubMgl,
            String usuarioMod, int perfilMod) throws ApplicationException {

        return manager.updateTecnoSub(cmtTecnologiaSubMgl, usuarioMod, perfilMod);
    }
    
    /**
     * Busca las tecnologias asociadas a un nodo.
     *
     * @author Victor Bocanegra
     * @param nodo
     * @return Tecnologias asociadas a un Subedificio.
     * @throws ApplicationException
     */
    @Override
    public List<CmtTecnologiaSubMgl> findTecnoSubByNodo(NodoMgl nodo) throws ApplicationException {

        return manager.findTecnoSubByNodo(nodo);
    }

    @Override
    public CmtPestanaPenetracionDto findListCMxTecnologias(HashMap<String, Object> params) throws ApplicationException{
       return manager.getListaCMxTec(params);
    }
    
        
     /**
     * Buscar una sub tecnologia basandose en su id
     *
     * @author Victor Bocanegra
     * @param idTecnoSub id de la tecnologia subedificio
     * @return Un elemento de tipo CmtTecnologiaSub
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @Override
    public CmtTecnologiaSubMgl findByIdTecnoSub(BigDecimal idTecnoSub) 
            throws ApplicationException {
        
        return manager.findIdTecnoSub(idTecnoSub);
    }
}
