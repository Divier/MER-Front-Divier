package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtDireccionMglManager;
import co.com.claro.mgl.dtos.ResponseValidarDireccionDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.error.ApplicationExceptionCM;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionSolicitudMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.visitasTecnicas.business.DireccionRRManager;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author User
 */
@Stateless
public class CmtDireccionMglFacade implements CmtDireccionMglFacadeLocal {

    private String user = "";
    private int perfil = 0;
    private final CmtDireccionMglManager cmtDireccionMglManager;

    public CmtDireccionMglFacade() {
        this.cmtDireccionMglManager = new CmtDireccionMglManager();
    }

    @Override
    public List<CmtDireccionMgl> findAll() throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CmtDireccionMgl create(CmtDireccionMgl t) throws ApplicationException {
        return cmtDireccionMglManager.create(t, user, perfil);
    }

    @Override
    public CmtDireccionMgl update(CmtDireccionMgl t) throws ApplicationException {
        return cmtDireccionMglManager.update(t, user, perfil);
    }

    @Override
    public boolean delete(CmtDireccionMgl t) throws ApplicationException {
        return cmtDireccionMglManager.delete(t, user, perfil);
    }

    @Override
    public CmtDireccionMgl findById(CmtDireccionMgl direccionMg) throws ApplicationException {
        return cmtDireccionMglManager.findById(direccionMg.getDireccionId());

    }

    public CmtDireccionMgl findByIdSolicitud(BigDecimal id) throws ApplicationException {
        return cmtDireccionMglManager.findByIdSolicitud(id);
    }

    @Override
    public CmtDireccionMgl findByIdSubEdificio(BigDecimal id) throws ApplicationException {
        return cmtDireccionMglManager.findByIdSubEdificio(id);
    }

    @Override
    public CmtDireccionMgl findByDirAlt(String id) throws ApplicationException {
        return cmtDireccionMglManager.findByDirAlt(id);
    }

    @Override
    public boolean findAny(CmtDireccionMgl newAltAddress) throws ApplicationException {
        return cmtDireccionMglManager.findAny(newAltAddress);
    }

    @Override
    public void setUser(String mUser, int mPerfil) throws ApplicationException {
        if (mUser.equals("") || mPerfil == 0) {
            throw new ApplicationException("El Usuario o perfil Nopueden ser nulos");
        }
        user = mUser;
        perfil = mPerfil;
    }

    @Override
    public Long countFindByCm(CmtCuentaMatrizMgl cmtCuentaMatrizMgl) throws ApplicationException {
        return cmtDireccionMglManager.countFindByCm(cmtCuentaMatrizMgl);
    }

    @Override
    public List<CmtDireccionMgl> findByCuentaMatriz(CmtCuentaMatrizMgl cmtCuentaMatrizMgl,
            int page, int numberOfResult, Constant.TYPE_QUERY type) throws ApplicationException {
        return cmtDireccionMglManager.findByCuentaMatriz(cmtCuentaMatrizMgl, page, numberOfResult, type);
    }

    @Override
    public List<CmtDireccionMgl> findBySubEdificio(CmtSubEdificioMgl cmtSubEdificioMgl) throws ApplicationException {
        return cmtDireccionMglManager.findBySubEdificio(cmtSubEdificioMgl);
    }

    @Override
    public List<CmtDireccionMgl> findDireccionesByCuentaMatriz(CmtCuentaMatrizMgl cuentaMatriz) throws ApplicationException {
        CmtDireccionMglManager manager = new CmtDireccionMglManager();
        return manager.findDireccionesByCuentaMatriz(cuentaMatriz);
    }

    @Override
    public CmtDireccionMgl actualizar(CmtSubEdificioMgl cmtSubEdificioMgl, CmtDireccionMgl cmtdireccionMgl, DrDireccion drDireccion,
            String selectBarrio, ResponseValidarDireccionDto responseValidarDireccionDto, String direccion,
            String usuarioVT, boolean isUpdate, String usuario, int perfil, boolean desdeFichas) throws ApplicationExceptionCM, ApplicationException {
        CmtDireccionMglManager manager = new CmtDireccionMglManager();
        return manager.actualizar(cmtSubEdificioMgl, cmtdireccionMgl, drDireccion, selectBarrio, responseValidarDireccionDto, direccion, usuarioVT, isUpdate, usuario, perfil, desdeFichas);
    }

    @Override
    public HhppMgl actualizarDireccionHHPP(HhppMgl hhppMod, HhppMgl hhppModOld, CmtDireccionMgl cmtdireccionMgl, String direccionCM, String nombreSubEdi, String solicitud, String tipoSolicitud,
            String usuarioVT, String usuario, int perfil, DrDireccion dirmodif) throws ApplicationException, ApplicationExceptionCM {
        CmtDireccionMglManager manager = new CmtDireccionMglManager();
        return manager.actualizarDireccionHHPP(hhppMod, hhppModOld, cmtdireccionMgl, direccionCM, nombreSubEdi, solicitud, tipoSolicitud,
                usuarioVT, usuario, perfil, dirmodif);

    }

    @Override
    public CmtDireccionMgl actualizarDireccionCmOSubEdificio(CmtDireccionMgl cmtdireccionMgl, CmtDireccionSolicitudMgl cmtdireccionSolMgl, int tipoDireccion, String direccion, CmtSubEdificioMgl subEdificioMgl,
            String usuarioVT, boolean isUpdate, String usuario, int perfil, DrDireccion dirModifica) throws ApplicationExceptionCM {
        CmtDireccionMglManager manager = new CmtDireccionMglManager();
        return manager.actualizarDireccionCmOSubEdificio(cmtdireccionMgl, cmtdireccionSolMgl, tipoDireccion, direccion, subEdificioMgl,
                usuarioVT, isUpdate, usuario, perfil, dirModifica);

    }

    @Override
    public List<AuditoriaDto> construirAuditoria(CmtDireccionMgl cmtDireccionMgl)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return cmtDireccionMglManager.construirAuditoria(cmtDireccionMgl);
    }

    @Override
    public DireccionRRManager estandarizarNombreSubEdificioDireccion(CmtDireccionMgl cmtdireccionMgl, String nombreSubEdi)
            throws ApplicationException, ApplicationException {
        return cmtDireccionMglManager.estandarizarNombreSubEdificioDireccion(cmtdireccionMgl, nombreSubEdi);
    }

    /**
     * {@inheritDoc }
     * 
     * @param direccion {@link CmtDireccionMgl} direcci&oacute;n digitada por el usuario
     * @return {@link List}&lt{@link CmtDireccionMgl}> Direcciones encontradas en base de datos
     * @throws ApplicationException Excepci&oacute;n lanzada por las consultas
     */
    @Override
    public List<CmtDireccionMgl> findByDireccion(CmtDireccionMgl direccion) throws ApplicationException {
        return cmtDireccionMglManager.findByDireccion(direccion);
    }

    @Override
    public HhppMgl actualizarDireccionHHPPModCm(HhppMgl hhppMod, HhppMgl hhppModOld,
            CmtDireccionMgl cmtdireccionMgl, String direccionCM,
            String nombreSubEdi, String solicitud, String tipoSolicitud,
            String usuarioVT, String usuario, int perfil, 
            CmtSubEdificioMgl ss, DrDireccion dirCambio,boolean esSubPropiaDir, String nombreSubedificio) throws ApplicationException, ApplicationExceptionCM {

        return cmtDireccionMglManager.actualizarDireccionHHPPModCm(hhppMod,
                hhppModOld, cmtdireccionMgl, direccionCM, nombreSubEdi,
                solicitud, tipoSolicitud, usuarioVT, usuario, perfil, ss, dirCambio, esSubPropiaDir, nombreSubedificio);
    }
    
             /**
     * Metodo para consultar las direccion alternas de una cuenta matriz edificio general
     * Autor: Juan David Hernandez
     * @param cuentaMatrizId
     * @param subEdificioId
     * @return List<CmtDireccionMgl>
     * @throws ApplicationException
     */
    @Override
    public List<CmtDireccionMgl> findDireccionAlternaByCmSub(BigDecimal cuentaMatrizId,
            BigDecimal subEdificioId) throws ApplicationException {
        return cmtDireccionMglManager.findDireccionAlternaByCmSub(cuentaMatrizId,subEdificioId);
    }
    
    /**
      * Metodo para consultar la cuenta matriz 
     * Autor: cardenaslb
     * @param cmtDireccionId
     * @return CmtDireccionMgl
     * @throws ApplicationException
     */
    @Override
    public CmtDireccionMgl findCmtDireccion(BigDecimal cmtDireccionId) throws ApplicationException {
        return cmtDireccionMglManager.findCmtDireccion(cmtDireccionId);
    }
    
    /**
     * Metodo para consultar una direccion de ccmm por id direccionMGL Autor:
     * Victor Bocanegra
     *
     * @param idDireccionMgl
     * @return CmtDireccionMgl
     * @throws ApplicationException
     */
     @Override
    public CmtDireccionMgl findCmtIdDireccion(BigDecimal idDireccionMgl) throws ApplicationException {
        return cmtDireccionMglManager.findCmtIdDireccion(idDireccionMgl);
    }

}
