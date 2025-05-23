/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

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
import javax.ejb.Local;

/**
 *
 * @author rodriguezluim
 */
@Local
public interface CmtTecnologiaSubMglFacadeLocal extends BaseCmFacadeLocal<CmtTecnologiaSubMgl> {

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
     void actualizarCostosTecnologiaSubEdificioGeneral(CmtCuentaMatrizMgl cuentaMatriz,
            CmtVisitaTecnicaMgl cmtVisitaTecnicaMgl, CmtBasicaMgl tecnologia) throws ApplicationException;

    /**
     * Permite encontrar la lista de las tecnologias en la tabla
     * cmt_tecnologiasub por subedificio
     *
     * @author cardenaslb
     * @param params
     * @param page
     * @param numeroRegistrosConsulta
     * @param cmtSubEdificioMgl
     * @param fyndBy
     * @return
     * @throws ApplicationException
     */
     CmtPestanaPenetracionDto findListTecnologiasPenetracionSubCM(HashMap<String, Object> params,
            int page, int numeroRegistrosConsulta, CmtSubEdificioMgl cmtSubEdificioMgl,
            Constant.FIND_HHPP_BY fyndBy) throws ApplicationException;

    /**
     * Permite contar las tecnologias en la tabla cmt_tecnologiasub por Torre
     *
     * @author cardenaslb
     * @param params
     * @param cmtSubEdificioMgl
     * @param fyndBy
     * @return
     * @throws ApplicationException
     */
     int countListPenetracionTecSub(HashMap<String, Object> params, CmtSubEdificioMgl cmtSubEdificioMgl, Constant.FIND_HHPP_BY fyndBy)
            throws ApplicationException;

    /**
     * Permite contar las tecnologias en la tabla cmt_tecnologiasub por CM
     *
     * @author cardenaslb
     * @param params
     * @param cmtSubEdificioMgl
     * @param fyndBy
     * @return
     * @throws ApplicationException
     */
     int countListPenetracionTecCM(HashMap<String, Object> params, CmtSubEdificioMgl cmtSubEdificioMgl,
            Constant.FIND_HHPP_BY fyndBy)
            throws ApplicationException;

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
     void actualizarCostosSubEdificios(CmtOrdenTrabajoMgl ordenTrabajo) throws ApplicationException;

    /**
     * Actualizar el camopo meta de las tecnologis sub edificio correspondientes
     * a una VT activa
     *
     * @author rodiguezluim
     * @param visitaTecnica
     * @throws ApplicationException
     */
     void guardarMetaSubEdificios(CmtVisitaTecnicaMgl visitaTecnica) throws ApplicationException;

    /**
     * Actualizar el campo FECHA HABILITACION de las tecnologis sub edificio
     * correspondientes a una VT activa
     *
     * @author rodiguezluim
     * @param visitaTecnica
     * @throws ApplicationException
     */
     void guardarFechaHabilitacionSubEdificios(CmtVisitaTecnicaMgl visitaTecnica) throws ApplicationException;

    /**
     * Actualizar el campo TIEMPO DE RECUPERACION de las tecnologis sub edificio
     * correspondientes a una VT activa
     *
     * @author rodiguezluim
     * @param visitaTecnica
     * @throws ApplicationException
     */
     void guardarTiempoRecuperacionSubEdificios(CmtVisitaTecnicaMgl visitaTecnica) throws ApplicationException;

    /**
     * Busca las tecnologias asociadas a un subedificio.
     *
     * @author Victor Bocanegra
     * @param cmtSubEdificioMgl SubEdificio
     * @return Tecnologias asociadas a un Subedificio.
     * @throws ApplicationException
     */
     List<CmtTecnologiaSubMgl> findTecnoSubBySubEdi(
            CmtSubEdificioMgl cmtSubEdificioMgl) throws ApplicationException;
    
    /**
     * valbuenayf metodo para realizar el reporte de cuentamatriz para el cargue
     * masivo
     *
     * @param params
     * @param usuario
     */
     void findReporteGeneralDetallado(Map<String, Object> params, String usuario);

    /**
     * valbuenayf metodo para contar la cantidad de registros del reporte de
     * cuentamatriz para el cargue masivo
     *
     * @param params
     * @return
     */
     Integer findCountRepGeneralDetallado(Map<String, Object> params);
    
    /**
     * valbuenayf metodo para actualizar los atributos de cuenta matriz
     *
     * @param listaModificacion
     * @param usuario
     * @param tipoModificacion
     * @param nombreArchivo
     * @param perfil
     */
    void actualizarCargueMasivoCuentaMatriz(Observer observer, List<MasivoModificacionDto> listaModificacion, String usuario, Integer tipoModificacion, int perfil, String nombreArchivo);

    /**
     * Autor: Victor Bocanegra Metodo para la creacion las tecnologias
     * subedificio configuradas en la gestion de la creacion de una CM
     *
     * @param datosGestion
     * @param cmtSubEdificioMgl
     * @param usuario
     * @param perfil
     */
     void crearTecnSubXGestion(Map<CmtBasicaMgl, NodoMgl> datosGestion,
            CmtSubEdificioMgl cmtSubEdificioMgl, String usuario, int perfil);
    
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
     void deleteSubEdificioTecnologia(CmtTecnologiaSubMgl cmtTecnologiaSubMgl,
            String usuario, int perfil) throws ApplicationException; 
    
        
        /**
     * Busca las tecnologias asociadas a un subedificio por tecnologia.
     *
     * @author Victor Bocanegra
     * @param cmtSubEdificioMgl
     * @param cmtBasicaMgl
     * @return Tecnologias asociadas a un Subedificio.
     * @throws ApplicationException
     */
     List<CmtTecnologiaSubMgl> findTecnoSubBySubEdiTec(CmtSubEdificioMgl cmtSubEdificioMgl, CmtBasicaMgl cmtBasicaMgl)
            throws ApplicationException;
    
    /**
     * Buscar una sub tecnologia basandose en un subedificio y la tecnologia
     *
     * @author rodriguezluim
     * @param subEdificio Condicional por el cual voy a buscar
     * @param cmtBasicaMgl
     * @return Un elemento de tipo CmtTecnologiaSub
     * @throws co.com.claro.mgl.error.ApplicationException
     */
     CmtTecnologiaSubMgl findBySubEdificioTecnologia(CmtSubEdificioMgl cmtSubEdificioMgl,
            CmtBasicaMgl cmtBasicaMgl) throws ApplicationException;
    
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
     CmtTecnologiaSubMgl updateTecnoSub(CmtTecnologiaSubMgl cmtTecnologiaSubMgl,
            String usuarioMod, int perfilMod) throws ApplicationException; 
    
    /**
     * Busca las tecnologias asociadas a un nodo.
     *
     * @author Victor Bocanegra
     * @param nodo
     * @return Tecnologias asociadas a un Subedificio.
     * @throws ApplicationException
     */
     List<CmtTecnologiaSubMgl> findTecnoSubByNodo(NodoMgl nodo) throws ApplicationException;
    
    
        /**
     * Permite encontrar la lista de las tecnologias en la tabla
     * cmt_tecnologiasub por cuenta matriz
     *
     * @author cardenaslb
     * @param params
     * @param page
     * @param numeroRegistrosConsulta
     * @return
     * @throws ApplicationException
     */
     CmtPestanaPenetracionDto findListCMxTecnologias(HashMap<String, Object> params) throws ApplicationException;
    
    
         /**
     * Buscar una sub tecnologia basandose en su id
     *
     * @author Victor Bocanegra
     * @param idTecnoSub id de la tecnologia subedificio
     * @return Un elemento de tipo CmtTecnologiaSub
     * @throws co.com.claro.mgl.error.ApplicationException
     */
      CmtTecnologiaSubMgl findByIdTecnoSub(BigDecimal idTecnoSub) 
            throws ApplicationException;

      CmtTecnologiaSubMgl crearTecnSubXPenetracion
        (CmtSubEdificioMgl subEdificioMgl, NodoMgl nodoSeleccionado, CmtBasicaMgl basicaIdEstadosTec, String usuarioVT, int perfilVT);

    
}
