/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.error.ApplicationExceptionCM;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudCmMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudHhppMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

/**
 *
 * @author gilaj
 */
@Local
public interface CmtSolicitudHhppMglFacadeLocal {

    /**
     * crea la solicitud de HHPP.
     *
     * @author Antonio Gil
     * @param cmtSolicitudHhppMgl
     * @return CmtSolicitudHhpp
     * @throws ApplicationException
     */
    public CmtSolicitudHhppMgl create(CmtSolicitudHhppMgl cmtSolicitudHhppMgl) throws ApplicationException;

    /**
     * Update solicitudes HHPP
     *
     * @author Antonio Gil
     * @param perfil
     * @param user
     * @return Object
     * @throws ApplicationException
     */
    public CmtSolicitudHhppMgl update(CmtSolicitudHhppMgl cmtSolicitudHhppMgl, String user, int perfil) throws ApplicationException;

    /**
     * Asigna el nombre del usuario conectado.
     *
     * @author Antonio Gil
     * @param mUser, mPerfil
     * @param mPerfil
     * @throws ApplicationException
     */
    public void setUser(String mUser, int mPerfil) throws ApplicationException;

    /**
     * Busca todas las solicitudes de HHPP.
     *
     * @author Antonio Gil
     * @return 
     * @throws ApplicationException
     */
    public List<CmtSolicitudHhppMgl> findAll() throws ApplicationException;

    /**
     * Busca todas las solicitudes de HHPP por el hhpp_id.
     *
     * @author Antonio Gil
     * @param hhpId
     * @return
     * @throws ApplicationException
     */
    public List<CmtSolicitudHhppMgl> findByHhpp(HhppMgl hhpId) throws ApplicationException;

    /**
     * Busca todas las solicitudes de HHPP por el id de solicitud
     *
     * @author Antonio Gil
     * @param solicitudId
     * @return
     * @throws ApplicationException
     */
    public List<CmtSolicitudHhppMgl> findBySolicitud(CmtSolicitudCmMgl solicitudId) throws ApplicationException;

    /**
     * Lista toda la informacion de las solicitudes hhpp por subedificios
     *
     * @author Antonio Gil
     * @param subEdificioId
     * @return resulList
     * @throws ApplicationException
     */
    public List<CmtSolicitudHhppMgl> findBySubEdificio(CmtSubEdificioMgl subEdificioId) throws ApplicationException;

    /**
     * Valida que el hhpp no exista en la BD
     *
     * @author Antonio Gil
     * @param cmtSolicitudHhppMgl
     * @return boolean
     * @throws ApplicationException
     */
    public String ValidadorDireccionHHPP(CmtSolicitudHhppMgl cmtSolicitudHhppMgl) throws ApplicationException;
    
    /**
     * Guarda las solicitudes de HHPP en BD
     *
     * @author Antonio Gil
     * @param perfil
     * @param user
     * @return boolean
     * @throws ApplicationException
     */
    public String GuardaListadoHHPP(List<CmtSolicitudHhppMgl> cmtSolicitudHhppMglListToChanges, CmtSolicitudCmMgl cmtSolicitudCmMgl, String user, int perfil) throws ApplicationException;
    
    /**
     * Validacion solicitudes de HHPP cambiadas
     *
     * @author Antonio Gil
     * @param listHhpp
     * @param listSolicitudHhppMgl
     * @return null
     * @throws ApplicationException
     */
    public List<HhppMgl> ValidacionCambiosHhpp(List<HhppMgl> listHhpp, List<CmtSolicitudHhppMgl> listSolicitudHhppMgl) throws ApplicationException;
    
    /**
     * Guarda las gestiones de HHPP en BD
     *
     * @author Antonio Gil
     * @param perfil {@code int}  Número de peril del usuario
     * @return null
     * @throws ApplicationException Excepción personalizada de la App.
     * @throws co.com.claro.mgl.error.ApplicationExceptionCM
     */
    Map<String, Object> guardaGestionHHPP(List<CmtSolicitudHhppMgl> cmtSolicitudHhppMglListToChanges, CmtSolicitudCmMgl cmtSolicitudCmMgl, String user, int perfil) throws ApplicationException, ApplicationExceptionCM;
    
    /**
     * Actualiza los estados de la Solicitud HHPP
     *
     * @author Antonio Gil
     * @param perfil
     * @param user
     * @return null
     * @throws ApplicationException
     */
    public List<CmtSolicitudHhppMgl> ActualizaCambiosHhpp(List<CmtSolicitudHhppMgl> listDeleteCmtSolicitudHhppMgl, CmtSolicitudHhppMgl cmtSolicitudHhppMgl, String user, int perfil) throws ApplicationException;

    /**
     * Verifica si existe el HHPP real en MER con la información de apartamento,
     * para poder realizar el traslado de HHPP bloqueado.
     *
     * @param cmtSolicitudHhppMgl {@link CmtSolicitudCmMgl}
     * @return {@code Map<String, Object>}
     * @author Gildardo Mora
     */
    Map<String, Object> validarApartHhppVirtual(CmtSolicitudHhppMgl cmtSolicitudHhppMgl);
}
