package co.com.claro.mgl.facade.cm;

/**
 *
 * @author Admin
 */
import co.com.claro.mgl.businessmanager.cm.CmtSolicitudCmMglManager;
import co.com.claro.mgl.dtos.CmtReporteDetalladoDto;
import co.com.claro.mgl.dtos.FiltroReporteSolicitudCMDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionSolicitudMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudCmMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoSolicitudMgl;
import co.com.telmex.catastro.data.AddressService;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;

/**
 *
 * @author Admin
 */
@Stateless
public class CmtSolicitudCmMglFacade implements CmtSolicitudCmMglFacadeLocal {

    private String user = "";
    private int perfil = 0;

    /**
     * Cuenta las Solicitudes asociadas a una CM. Permite realizar el conteo de
     * las Solicitudes asociadas a una Cuenta Matriz en el repositorio.
     *
     * @author Johnnatan Ortiz
     * @param cuentaMatriz ID de la Cuenta Matriz
     * @return Solicitudes encontradas en el repositorio asociadas a una Cuenta
     * Matriz
     * @throws ApplicationException
     */
    @Override
    public int getCountByCuentaMatriz(CmtCuentaMatrizMgl cuentaMatriz) throws ApplicationException {
        CmtSolicitudCmMglManager manager = new CmtSolicitudCmMglManager();
        return manager.getCountByCuentaMatriz(cuentaMatriz);
    }

    /**
     * Busca las Solicitudes asociadas a una CM.Permite realizar la busqueda de
 las Solicitudes asociadas a una Cuenta Matriz en el repositorio
 realizando paginacion de los resultados.
     *
     * @author Johnnatan Ortiz
     * @param cuentaMatriz ID de la Cuenta Matriz
     * @return Solicitudes encontradas en el repositorio asociadas a una Cuenta
     * Matriz
     * @throws ApplicationException
     */
    @Override
    public List<CmtSolicitudCmMgl> findByCuentaMatrizPaginado(CmtCuentaMatrizMgl cuentaMatriz ) throws ApplicationException {
        CmtSolicitudCmMglManager manager = new CmtSolicitudCmMglManager();
        return manager.findByCuentaMatrizPaginado( cuentaMatriz );
    }

    public List<CmtSolicitudCmMgl> findAll() throws ApplicationException {
        CmtSolicitudCmMglManager cmtSolicitudCmMglManager
                = new CmtSolicitudCmMglManager();
        return cmtSolicitudCmMglManager.findAll();
    }

    
    
    @Override
    public CmtSolicitudCmMgl crearSol(CmtSolicitudCmMgl t) throws ApplicationException {
        CmtSolicitudCmMglManager cmtSolicitudCmMglManager
                = new CmtSolicitudCmMglManager();
        return cmtSolicitudCmMglManager.create(t, user, perfil);
    }

    @Override
    public CmtSolicitudCmMgl update(CmtSolicitudCmMgl t) throws ApplicationException {
        CmtSolicitudCmMglManager cmtSolicitudCmMglManager
                = new CmtSolicitudCmMglManager();
        return cmtSolicitudCmMglManager.update(t, user, perfil);
    }

    @Override
    public CmtSolicitudCmMgl updateSolicitudCreaCM(CmtSolicitudCmMgl solicitudCm,
            AddressService addressServiceGestion, Map<CmtBasicaMgl, 
                    NodoMgl> datosGestion, NodoMgl nodoXdefecto ,boolean  isFichaNodos,
                    String usuarioSe, int perfilSe) 
            throws ApplicationException, ApplicationException {  //CmtNodoValidado cmtNodoValidado
        CmtSolicitudCmMglManager cmtSolicitudCmMglManager
                = new CmtSolicitudCmMglManager();
        return cmtSolicitudCmMglManager.updateSolicitudCreaCM
                (solicitudCm, addressServiceGestion, datosGestion, 
                nodoXdefecto, usuarioSe, perfilSe , isFichaNodos);//cmtNodoValidado
    }

    public boolean delete(CmtSolicitudCmMgl t) throws ApplicationException {
        CmtSolicitudCmMglManager cmtSolicitudCmMglManager
                = new CmtSolicitudCmMglManager();
        return cmtSolicitudCmMglManager.delete(t);
    }

    public CmtSolicitudCmMgl findById(CmtSolicitudCmMgl sqlData) throws ApplicationException {
        CmtSolicitudCmMglManager cmtSolicitudCmMglManager
                = new CmtSolicitudCmMglManager();
        return cmtSolicitudCmMglManager.findById(sqlData);
    }

    /**
     * Busca por el id de la tabla
     *
     * @author gilaj
     * @param id Identificador unico de la tabla
     * @return cmtSolicitudCmMglManager
     * @throws ApplicationException
     */
    @Override
    public CmtSolicitudCmMgl findById(BigDecimal id)
            throws ApplicationException {
        CmtSolicitudCmMglManager cmtSolicitudCmMglManager
                = new CmtSolicitudCmMglManager();
        return cmtSolicitudCmMglManager.findById(id);
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
     * Cuenta las Solicitudes por filtro.Permite realizar el conteo de las
 solicitudes por los parametros de comunidad, division, segmento y lista
 de tipos de solicitudes.
     *
     * @author Johnnatan Ortiz
     * @param division division de las solicitudes
     * @param comunidad comunidad de las solicitudes
     * @param segmento segmento de las solicitudes
     * @param llamada
     * @param tipoSolicitudList lista de los tipos de solicitudes a consultar
     * @return Compentencias asociadas a un SubEdifico
     * @throws ApplicationException
     */
    @Override
    public int getCountPendientesByFiltroParaGestion(String division, String comunidad,
            CmtBasicaMgl segmento, List<CmtTipoSolicitudMgl> tipoSolicitudList,
            boolean llamada)
            throws ApplicationException {
        CmtSolicitudCmMglManager manager = new CmtSolicitudCmMglManager();
        return manager.getCountPendientesByFiltroParaGestion(division, comunidad,
                segmento, tipoSolicitudList, llamada);
    }

    /**
     * Obtiene las Solicitudes por filtro.Permite realizar el conteo de las
 solicitudes por los parametros de comunidad, division, segmento y lista
 de tipos de solicitudes.
     *
     * @author Johnnatan Ortiz
     * @param paginaSelected pagina de la busqueda
     * @param maxResults maximo numero de resultados
     * @param division division de las solicitudes
     * @param comunidad comunidad de las solicitudes
     * @param segmento segmento de las solicitudes
     * @param llamada
     * @param tipoSolicitudList lista de los tipos de solicitudes a consultar
     * @param ordenMayorMenor
     * @return Compentencias asociadas a un SubEdifico
     * @throws ApplicationException
     */
    @Override
    public List<CmtSolicitudCmMgl> findPendientesByFiltroParaGestionPaginacion(
            int paginaSelected, int maxResults,
            String division, String comunidad,
            CmtBasicaMgl segmento, List<CmtTipoSolicitudMgl> tipoSolicitudList,
            boolean llamada, boolean ordenMayorMenor)
            throws ApplicationException {
        CmtSolicitudCmMglManager manager = new CmtSolicitudCmMglManager();
        return manager.findPendientesByFiltroParaGestionPaginacion(paginaSelected, maxResults,
                division, comunidad,
                segmento, tipoSolicitudList, llamada, ordenMayorMenor);
    }

    /**
     * Cuenta las Solicitudes creadas el dia de la fecha actual. Permite
     * realizar el conteo de las solicitudes por tipos y creadas en la fecha
     * actual.
     *
     * @author Johnnatan Ortiz
     * @param tipoSolicitudList lista de los tipos de solicitudes a consultar
     * @return solicitudes
     * @throws ApplicationException
     */
    @Override
    public int getCountSolicitudCreateDay(List<CmtTipoSolicitudMgl> tipoSolicitudList) throws ApplicationException {
        CmtSolicitudCmMglManager manager = new CmtSolicitudCmMglManager();
        return manager.getCountSolicitudCreateDay(tipoSolicitudList);
    }

    /**
     * Cuenta las Solicitudes gestionadas el dia de la fecha actual. Permite
     * realizar el conteo de las solicitudes por tipos y gestionadas en la fecha
     * actual.
     *
     * @author Johnnatan Ortiz
     * @param tipoSolicitudList lista de los tipos de solicitudes a consultar
     * @return solicitudes
     * @throws ApplicationException
     */
    @Override
    public int getCountSolicitudGestionadaDay(List<CmtTipoSolicitudMgl> tipoSolicitudList) throws ApplicationException {
        CmtSolicitudCmMglManager manager = new CmtSolicitudCmMglManager();
        return manager.getCountSolicitudGestionadaDay(tipoSolicitudList);
    }

    /**
     * Cuenta las Solicitudes activas el dia de la fecha actual. Permite
     * realizar el conteo de las solicitudes por tipos y creadas en la fecha
     * actual.
     *
     * @author Johnnatan Ortiz
     * @param tipoSolicitudList lista de los tipos de solicitudes a consultar
     * @return solicitudes
     * @throws ApplicationException
     */
    @Override
    public int getCountAllSolicitudActiveDay(List<CmtTipoSolicitudMgl> tipoSolicitudList) throws ApplicationException {
        CmtSolicitudCmMglManager manager = new CmtSolicitudCmMglManager();
        return manager.getCountAllSolicitudActiveDay(tipoSolicitudList);
    }

    /**
     * Cuenta las Solicitudes por vencer en la fecha actual. Permite realizar el
     * conteo de las solicitudes por tipos y creadas en la fecha actual.
     *
     * @author Johnnatan Ortiz
     * @param tipoSolicitudList lista de los tipos de solicitudes a consultar
     * @return solicitudes
     * @throws ApplicationException
     */
    @Override
    public int getCountAllSolicitudPorVencerDay(List<CmtTipoSolicitudMgl> tipoSolicitudList) throws ApplicationException {
        CmtSolicitudCmMglManager manager = new CmtSolicitudCmMglManager();
        return manager.getCountAllSolicitudPorVencerDay(tipoSolicitudList);
    }

    /**
     * Cuenta las Solicitudes vencidas en la fecha actual. Permite realizar el
     * conteo de las solicitudes por tipos y creadas en la fecha actual.
     *
     * @author Johnnatan Ortiz
     * @param tipoSolicitudList lista de los tipos de solicitudes a consultar
     * @return solicitudes
     * @throws ApplicationException
     */
    @Override
    public int getCountAllSolicitudVencidasDay(List<CmtTipoSolicitudMgl> tipoSolicitudList) throws ApplicationException {
        CmtSolicitudCmMglManager manager = new CmtSolicitudCmMglManager();
        return manager.getCountAllSolicitudVencidasDay(tipoSolicitudList);
    }

    /**
     * Gestiona una solicitud VT. Permite realizar la gestion de una solicitud
     * de VT.
     *
     * @author Johnnatan Ortiz
     * @param solicitudCm solicitud VT a gestionar
     * @return solicitud gestionada
     * @throws ApplicationException
     */
    @Override
    public CmtSolicitudCmMgl gestionSolicitudVt(CmtSolicitudCmMgl solicitudCm) throws ApplicationException {
        CmtSolicitudCmMglManager manager = new CmtSolicitudCmMglManager();
        return manager.gestionSolicitudVt(solicitudCm, user, perfil);
    }

    @Override
    public List<AuditoriaDto> construirAuditoria(CmtSolicitudCmMgl cmtSolicitudCmMgl)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        CmtSolicitudCmMglManager manager = new CmtSolicitudCmMglManager();
        return manager.construirAuditoria(cmtSolicitudCmMgl);
    }

    /**
     * Bloquea o Desbloquea una solicitud.Permite realizar el bloqueo o
 desbloqueo de una solicitud en el repositorio para la gestion.
     *
     * @author Johnnatan Ortiz
     * @param solicitudCm solicitud a bloquear o desbloquear
     * @param bloqueo si es bloqueo o no
     * @return solicitud
     * @throws ApplicationException
     */
    @Override
    public CmtSolicitudCmMgl bloquearDesbloquearSolicitud(CmtSolicitudCmMgl solicitudCm,
            boolean bloqueo) throws ApplicationException {
        CmtSolicitudCmMglManager manager = new CmtSolicitudCmMglManager();
        return manager.bloquearDesbloquearSolicitud(solicitudCm, bloqueo, user, perfil);
    }
    /**
     * Guarda la gestion de HHPP
     *
     * @author Antonio Gil
     * @param solicitudCm solicitud VT a gestionar
     * @param usuario usuario gestionar
     * @param perfil perfil gestionar
     * @return solicitud actualizada
     * @throws ApplicationException
     */
    @Override
    public CmtSolicitudCmMgl gestionSolicitudHhpp(CmtSolicitudCmMgl solicitudCm, String usuario, int perfil)
            throws ApplicationException {
        CmtSolicitudCmMglManager manager = new CmtSolicitudCmMglManager();
        return manager.gestionSolicitudHhpp(solicitudCm, usuario, perfil);
    }

    @Override
    public int validaLongitudDireccion(CmtSolicitudCmMgl sol, CmtDireccionSolicitudMgl cdsm) throws ApplicationException {
        CmtSolicitudCmMglManager manager = new CmtSolicitudCmMglManager();
        return manager.validaLongitudDireccion(sol, cdsm);
    }
    
    /**
     * {@inheritDoc }
     * 
     * @param cuentaMatrizId Identificador de la cuetna matriz asociada a la solicitud
     * @return {@link Integer} cantidad de registros existentes en la base de datos
     * @throws ApplicationException 
     */
    @Override
    public Integer cantidadSolicitudesEliminacionPorCuentaMatriz(BigDecimal cuentaMatrizId) throws ApplicationException {
        CmtSolicitudCmMglManager manager = new CmtSolicitudCmMglManager();
        return manager.cantidadSolicitudesEliminacionPorCuentaMatriz(cuentaMatrizId);
    }

    @Override
    public void getSolicitudesDetallado(String tipoReporte, CmtTipoSolicitudMgl cmtTipoSolicitudMgl, Date fechaInicio, Date fechaFin, BigDecimal estado, String usuario) throws ApplicationException {
        CmtSolicitudCmMglManager manager = new CmtSolicitudCmMglManager();
        manager.getSolicitudesDetalladoSolicitudes(tipoReporte, cmtTipoSolicitudMgl, fechaInicio, fechaFin, estado, usuario);
    }
    
      @Override
    public boolean validarExisteSolCMTipo(CmtCuentaMatrizMgl cmtCuentaMatrizMgl, CmtSolicitudCmMgl t) throws ApplicationException {
        CmtSolicitudCmMglManager manager = new CmtSolicitudCmMglManager();
        return manager.validarExisteSolCMTipo(cmtCuentaMatrizMgl, t);
    }
      
      /**
     * Consulta solicitud por cuenta matriz y tipo de solicitud
     *
     * @author Victor Bocanegra
     * @param cuentaMatrizId  cuenta matriz a consultar
     * @param cmtTipoSolicitudMgl tipo de solicitud a consultar
     * @param estadoSol
     * @return CmtSolicitudCmMgl
     * @throws ApplicationException
     */
    @Override  
    public CmtSolicitudCmMgl findBySolCMTipoSol(CmtCuentaMatrizMgl cuentaMatrizId, 
            CmtTipoSolicitudMgl cmtTipoSolicitudMgl, CmtBasicaMgl estadoSol) throws ApplicationException {
        
        CmtSolicitudCmMglManager manager = new CmtSolicitudCmMglManager();
        return manager.findBySolCMTipoSol(cuentaMatrizId, cmtTipoSolicitudMgl, estadoSol);
    }
    
   /**
     * Obtiene el conteo de registros del reporte detallado de solicitudes
     *
     * @author Victor Bocanegra 
     * @param tipoReporte
     * @param cmtTipoSolicitudMgl
     * @param fechaInicio
     * @param fechaFin
     * @param estado
     * @param usuario
     * @param page
     * @param numeroRegistros
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */
    @Override
    public int generarReporteDetalladoContar(String tipoReporte, CmtTipoSolicitudMgl cmtTipoSolicitudMgl,
            Date fechaInicio, Date fechaFin, BigDecimal estado, long page, int numeroRegistros, String usuario)
            throws ApplicationException {
        
        CmtSolicitudCmMglManager manager = new CmtSolicitudCmMglManager();
        return manager.generarReporteDetalladoContar(tipoReporte, cmtTipoSolicitudMgl, fechaInicio, fechaFin,
                estado, page, numeroRegistros, usuario);
      
    }
    
    /* Consulta listado de solicitudes agrupadas
     *
     * @author Victor Bocanegra
     * @param tipoReporte tipo de reporte
     * @param cmtTipoSolicitudMgl tipo de solicitud a consultar
     * @param fechaInicio fecha inicio rango
     * @param fechaFin fecha fin rango
     * @param estado de la solicitud.
     * @return List<CmtReporteGeneralDto>
     * @throws ApplicationException
     */
    @Override
    public FiltroReporteSolicitudCMDto getReporteGeneralSolicitudesSearchFinalCon(String tipoReporte,
            BigDecimal cmtTipoSolicitudMgl, Date fechaInicio, Date fechaFin, 
            BigDecimal estado,int firstResult, int maxResults)
            throws ApplicationException {

        CmtSolicitudCmMglManager manager = new CmtSolicitudCmMglManager();
        return manager.getReporteGeneralSolicitudesSearchFinalCon
        (tipoReporte, cmtTipoSolicitudMgl, fechaInicio, fechaFin, estado, firstResult, maxResults);

    }
    
    @Override
    public CmtSolicitudCmMgl updateCm(CmtSolicitudCmMgl t) throws ApplicationException {
        CmtSolicitudCmMglManager cmtSolicitudCmMglManager = new CmtSolicitudCmMglManager();
        return cmtSolicitudCmMglManager.updateCm(t, user, perfil);
    }
    
    /**
     * Obtiene una Solicitud por rol
     *
     * @author Victor Bocanegra
     * @param tipoSolicitudList lista de los tipos de solicitudes a consultar
     * @param solicitudId id de la solicitud a consultar
     * @return CmtSolicitudCmMgl
     * @throws ApplicationException
     */
    @Override
    public CmtSolicitudCmMgl findBySolicitudPorPermisos(
            List<CmtTipoSolicitudMgl> tipoSolicitudList,
            BigDecimal solicitudId) throws ApplicationException {

        CmtSolicitudCmMglManager cmtSolicitudCmMglManager = new CmtSolicitudCmMglManager();
        return cmtSolicitudCmMglManager.findBySolicitudPorPermisos(tipoSolicitudList, solicitudId);
    }

    @Override
    public void envioCorreoGestion(CmtSolicitudCmMgl t) {
        CmtSolicitudCmMglManager cmtSolicitudCmMglManager = new CmtSolicitudCmMglManager();
        cmtSolicitudCmMglManager.enviarCorreoGestion(t);
    }
    
    public List<CmtReporteDetalladoDto> getSolicitudesSearchDetalle(String tipoReporte,
            CmtTipoSolicitudMgl cmtTipoSolicitudMgl, Date fechaInicio, Date fechaFin,
            BigDecimal estado, int inicio, int fin, String usuario,
            int procesados, int regProcesar) throws ApplicationException {
        CmtSolicitudCmMglManager cmtSolicitudCmMglManager = new CmtSolicitudCmMglManager();
        return cmtSolicitudCmMglManager.getSolicitudesSearch(tipoReporte,
                cmtTipoSolicitudMgl, fechaInicio, fechaFin,
                estado, inicio, fin, usuario,
                procesados, regProcesar);
    }
    
}
