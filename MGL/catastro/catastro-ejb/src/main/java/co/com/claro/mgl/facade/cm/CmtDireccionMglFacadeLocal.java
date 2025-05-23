package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.dtos.ResponseValidarDireccionDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.error.ApplicationExceptionCM;
import co.com.claro.mgl.facade.BaseFacadeLocal;
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

/**
 *
 * @author Fabian Barrera
 */
public interface CmtDireccionMglFacadeLocal extends BaseFacadeLocal<CmtDireccionMgl> {

    public CmtDireccionMgl findByDirAlt(String id) throws ApplicationException;

    public CmtDireccionMgl findByIdSubEdificio(BigDecimal id) throws ApplicationException;

    public boolean findAny(CmtDireccionMgl newAltAddress) throws ApplicationException;

    @Override
    public CmtDireccionMgl update(CmtDireccionMgl t) throws ApplicationException;

    @Override
    public CmtDireccionMgl create(CmtDireccionMgl t) throws ApplicationException;

    @Override
    public boolean delete(CmtDireccionMgl t) throws ApplicationException;

    public List<CmtDireccionMgl> findDireccionesByCuentaMatriz(CmtCuentaMatrizMgl cuentaMatriz) throws ApplicationException;

    public void setUser(String mUser, int mPerfil) throws ApplicationException;

    public Long countFindByCm(CmtCuentaMatrizMgl cmtCuentaMatrizMgl) throws ApplicationException;

    public List<CmtDireccionMgl> findByCuentaMatriz(CmtCuentaMatrizMgl cmtCuentaMatrizMgl,
            int page, int numberOfResult, Constant.TYPE_QUERY type) throws ApplicationException;

    public List<CmtDireccionMgl> findBySubEdificio(CmtSubEdificioMgl CmtSubEdificioMgl) throws ApplicationException;

    public CmtDireccionMgl actualizar(CmtSubEdificioMgl cmtSubEdificioMgl, CmtDireccionMgl cmtdireccionMgl, DrDireccion drDireccion,
            String selectBarrio, ResponseValidarDireccionDto responseValidarDireccionDto, String direccion,
            String usuarioVT, boolean isUpdate, String usuario, int perfil, boolean desdeFichas) throws ApplicationExceptionCM, ApplicationException;

    public HhppMgl actualizarDireccionHHPP(HhppMgl hhppMod, HhppMgl hhppModOld, CmtDireccionMgl cmtdireccionMgl, String direccionCM, String nombreSubEdi, String solicitud, String tipoSolicitud,
            String usuarioVT, String usuario, int perfil, DrDireccion dirmodif ) throws ApplicationException, ApplicationExceptionCM;

    public CmtDireccionMgl actualizarDireccionCmOSubEdificio(CmtDireccionMgl cmtdireccionMgl, CmtDireccionSolicitudMgl cmtdireccionSolMgl, int tipoDireccion, String direccion, CmtSubEdificioMgl subEdificioMgl,
            String usuarioVT, boolean isUpdate, String usuario, int perfil, DrDireccion dirModifica) throws ApplicationExceptionCM;

    public List<AuditoriaDto> construirAuditoria(CmtDireccionMgl cmtDireccionMgl)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;

    public DireccionRRManager estandarizarNombreSubEdificioDireccion(CmtDireccionMgl cmtdireccionMgl, String nombreSubEdi)
            throws ApplicationException, ApplicationException;

    /**
     * M&eacute;todo para encontrar una direcci&oacute;n en base de datos por la direcci&oacute;n digitada
     * 
     * @param direccion {@link CmtDireccionMgl} direcci&oacute;n digitada por el usuario
     * @return {@link List}&lt{@link CmtDireccionMgl}> Direcciones encontradas en base de datos
     * @throws ApplicationException Excepci&oacute;n lanzada por las consultas
     */
    List<CmtDireccionMgl> findByDireccion(CmtDireccionMgl direccion) throws ApplicationException;
    
     public HhppMgl actualizarDireccionHHPPModCm(HhppMgl hhppMod, HhppMgl hhppModOld,
            CmtDireccionMgl cmtdireccionMgl, String direccionCM,
            String nombreSubEdi, String solicitud, String tipoSolicitud,
            String usuarioVT, String usuario, int perfil, 
            CmtSubEdificioMgl ss, DrDireccion dirCambio,boolean esSubPropiaDir, String nombreTorre) throws ApplicationException, ApplicationExceptionCM;
     
    /**
     * Metodo para consultar las direccion alternas de una cuenta matriz
     * edificio general Autor: Juan David Hernandez
     *
     * @param cuentaMatrizId
     * @param subEdificioId
     * @return List<CmtDireccionMgl>
     * @throws ApplicationException
     */
    List<CmtDireccionMgl> findDireccionAlternaByCmSub(BigDecimal cuentaMatrizId,
            BigDecimal subEdificioId) throws ApplicationException;
    
    
     /**
      * Metodo para consultar la cuenta matriz 
     * Autor: cardenaslb
     * @param cmtDireccionId
     * @return CmtDireccionMgl
     * @throws ApplicationException
     */
    CmtDireccionMgl findCmtDireccion(BigDecimal cmtDireccionId)
             throws ApplicationException;
    
        /**
     * Metodo para consultar una direccion de ccmm por id direccionMGL Autor:
     * Victor Bocanegra
     *
     * @param idDireccionMgl
     * @return CmtDireccionMgl
     * @throws ApplicationException
     */
     CmtDireccionMgl findCmtIdDireccion(BigDecimal idDireccionMgl) throws ApplicationException;
}
