
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtSolictudHhppMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudCmMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudHhppMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;

/**
 *
 * @author gilaj
 */
@Stateless
public class CmtSolicitudHhppMglFacade implements CmtSolicitudHhppMglFacadeLocal {

    private String user = "";
    private int perfil = 0;
    private CmtSolictudHhppMglManager cmtSolictudHhppMglManager = new CmtSolictudHhppMglManager();
    
    /**
     * crea la solicitud de Hhpp
     *
     * @author Antonio Gil
     * @throws ApplicationException
     */
    @Override
    public CmtSolicitudHhppMgl create(CmtSolicitudHhppMgl cmtSolicitudHhppMgl) throws ApplicationException {
        return cmtSolictudHhppMglManager.create(cmtSolicitudHhppMgl,user,perfil);
    }

    /**
     * Asigna el usuario y perfil 
     *
     * @author Antonio Gil
     * @param mUser
     * @param mPerfil
     * @throws ApplicationException
     */
    @Override
    public void setUser(String mUser, int mPerfil) throws ApplicationException {
        if (mUser.equals("") || mPerfil == 0) {
            throw new ApplicationException("El Usuario o perfil Nopueden ser nulos");
        }
        user = mUser;
        perfil = mPerfil;
    }
    
    /**
     * actualiza la solicitud de Hhpp
     *
     * @author Antonio Gil
     * @param perfil
     * @param user
     * @throws ApplicationException
     */
    @Override
    public CmtSolicitudHhppMgl update(CmtSolicitudHhppMgl cmtSolicitudHhppMgl, String user, int perfil) throws ApplicationException {
        return  cmtSolictudHhppMglManager.update(cmtSolicitudHhppMgl, user, perfil);
    }
    
    /**
     * busca todas las solicitudes de Hhpp
     *
     * @author Antonio Gil
     * @return 
     * @throws ApplicationException
     */
    @Override
    public List<CmtSolicitudHhppMgl> findAll() throws ApplicationException {
        return cmtSolictudHhppMglManager.findAll();
    }
    
    /**
     * Busca las solicitudes por HHPP
     *
     * @author Antonio Gil
     * @param hhpId
     * @return 
     * @throws ApplicationException
     */
    @Override
    public List<CmtSolicitudHhppMgl> findByHhpp(HhppMgl hhpId) throws ApplicationException {
        return cmtSolictudHhppMglManager.findByHhpp(hhpId);
    }
    
    /**
     * Busca las solicitudes por HHPP
     *
     * @author Antonio Gil
     * @param solicitudId
     * @return 
     * @throws ApplicationException
     */
    @Override
    public List<CmtSolicitudHhppMgl> findBySolicitud(CmtSolicitudCmMgl solicitudId) throws ApplicationException {
        return cmtSolictudHhppMglManager.findBySolicitud(solicitudId);
    }
    
    /**
     * Busca las solicitudes por subedificios
     *
     * @author Antonio Gil
     * @param subEdificioId
     * @throws ApplicationException
     */
    @Override
    public List<CmtSolicitudHhppMgl> findBySubEdificio(CmtSubEdificioMgl subEdificioId) throws ApplicationException {
        return cmtSolictudHhppMglManager.findBySubEdificio(subEdificioId);
    }
    
    /**
     * Valida que el hhpp no exista en la BD
     *
     * @author Antonio Gil
     * @param cmtSolicitudHhppMgl
     * @return boolean
     * @throws ApplicationException
     */
    @Override
    public String ValidadorDireccionHHPP(CmtSolicitudHhppMgl cmtSolicitudHhppMgl) throws ApplicationException {
        return cmtSolictudHhppMglManager.ValidadorDireccionHHPP(cmtSolicitudHhppMgl);
    }
    
    /**
     * Guarda las solicitudes de HHPP en BD
     *
     * @author Antonio Gil
     * @param perfil
     * @param user
     * @return boolean
     * @throws ApplicationException
     */
    @Override
    public String GuardaListadoHHPP(List<CmtSolicitudHhppMgl> cmtSolicitudHhppMglListToChanges,CmtSolicitudCmMgl cmtSolicitudCmMgl,String user, int perfil) throws ApplicationException {
        return cmtSolictudHhppMglManager.GuardaListadoHHPP(cmtSolicitudHhppMglListToChanges, cmtSolicitudCmMgl, user, perfil);
    }
    /**
     * Guarda las gestiones de HHPP en BD
     *
     * @author Antonio Gil
     * @param perfil {@code int}  Número de peril del usuario
     * @return null
     * @throws ApplicationException Excepción personalizada de la App.
     */
    @Override
    public Map<String, Object> guardaGestionHHPP(List<CmtSolicitudHhppMgl> cmtSolicitudHhppMglListToChanges, CmtSolicitudCmMgl cmtSolicitudCmMgl, String user, int perfil) throws ApplicationException {
        return cmtSolictudHhppMglManager.guardaGestionHHPP(cmtSolicitudHhppMglListToChanges, cmtSolicitudCmMgl, user, perfil);
    }

    /**
     * Validacion solicitudes de HHPP cambiadas
     *
     * @author Antonio Gil
     * @param listHhpp
     * @param listSolicitudHhppMgl
     * @return null
     * @throws ApplicationException
     */    
    @Override
    public List<HhppMgl> ValidacionCambiosHhpp(List<HhppMgl> listHhpp,List<CmtSolicitudHhppMgl> listSolicitudHhppMgl) throws ApplicationException {
        return cmtSolictudHhppMglManager.ValidacionCambiosHhpp(listHhpp, listSolicitudHhppMgl);
    }
    
    /**
     * Actualiza los estados de la Solicitud HHPP
     *
     * @author Antonio Gil
     * @param perfil
     * @param user
     * @return null
     * @throws ApplicationException
     */    
    @Override
    public List<CmtSolicitudHhppMgl> ActualizaCambiosHhpp(List<CmtSolicitudHhppMgl> listDeleteCmtSolicitudHhppMgl, CmtSolicitudHhppMgl cmtSolicitudHhppMgl, String user, int perfil) throws ApplicationException {
        return cmtSolictudHhppMglManager.ActualizaCambiosHhpp(listDeleteCmtSolicitudHhppMgl, cmtSolicitudHhppMgl, user, perfil);
    }

    /**
     * Verifica si existe el HHPP real en MER con la información de apartamento,
     * para poder realizar el traslado de HHPP bloqueado.
     *
     * @param cmtSolicitudHhppMgl {@link CmtSolicitudCmMgl}
     * @return {@code Map<String, Object>}
     * @author Gildardo Mora
     */
    @Override
    public Map<String, Object> validarApartHhppVirtual(CmtSolicitudHhppMgl cmtSolicitudHhppMgl) {
        return cmtSolictudHhppMglManager.validarApartHhppVirtual(cmtSolicitudHhppMgl);
    }

}
