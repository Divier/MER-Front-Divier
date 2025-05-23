package co.com.claro.mgl.facade.cm;


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

/**
 *
 * @author Admin
 */
public interface CmtSolicitudCmMglFacadeLocal {

    public int getCountByCuentaMatriz(CmtCuentaMatrizMgl cuentaMatriz) throws ApplicationException;

    public List<CmtSolicitudCmMgl> findByCuentaMatrizPaginado( CmtCuentaMatrizMgl cuentaMatriz )
            throws ApplicationException;

    public CmtSolicitudCmMgl findById(BigDecimal id)
            throws ApplicationException;

    public CmtSolicitudCmMgl crearSol(CmtSolicitudCmMgl t) throws ApplicationException;

    public void setUser(String mUser, int mPerfil) throws ApplicationException;

    public CmtSolicitudCmMgl update(CmtSolicitudCmMgl t) throws ApplicationException;

    public CmtSolicitudCmMgl updateSolicitudCreaCM(CmtSolicitudCmMgl solicitudCm,
            AddressService addressServiceGestion, Map<CmtBasicaMgl,
            NodoMgl> datosGestion,NodoMgl nodoXdefecto ,boolean  isFichaNodos,
            String usuarioSe, int perfilSe) //CmtNodoValidado cmtNodoValidado
            throws ApplicationException, ApplicationException;

    /**
     * Cuenta las Solicitudes por filtro.Permite realizar el conteo de las
     * solicitudes por los parametros de comunidad, division, segmento y lista
     * de tipos de solicitudes.
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
    public int getCountPendientesByFiltroParaGestion(String division,
            String comunidad,
            CmtBasicaMgl segmento,
            List<CmtTipoSolicitudMgl> tipoSolicitudList,
            boolean llamada) throws ApplicationException;

    /**
     * Obtiene las Solicitudes por filtro.Permite realizar el conteo de las
     * solicitudes por los parametros de comunidad, division, segmento y lista
     * de tipos de solicitudes.
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
    public List<CmtSolicitudCmMgl> findPendientesByFiltroParaGestionPaginacion(int paginaSelected,
            int maxResults,
            String division,
            String comunidad,
            CmtBasicaMgl segmento,
            List<CmtTipoSolicitudMgl> tipoSolicitudList,
            boolean llamada, boolean ordenMayorMenor) throws ApplicationException;

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
    public int getCountSolicitudCreateDay(List<CmtTipoSolicitudMgl> tipoSolicitudList) throws ApplicationException;

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
    public int getCountSolicitudGestionadaDay(List<CmtTipoSolicitudMgl> tipoSolicitudList) throws ApplicationException;

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
    public int getCountAllSolicitudActiveDay(List<CmtTipoSolicitudMgl> tipoSolicitudList) throws ApplicationException;

    /**
     * Cuenta las Solicitudes por vencer en la fecha actual. Permite realizar el
     * conteo de las solicitudes por tipos y creadas en la fecha actual.
     *
     * @author Johnnatan Ortiz
     * @param tipoSolicitudList lista de los tipos de solicitudes a consultar
     * @return solicitudes
     * @throws ApplicationException
     */
    public int getCountAllSolicitudPorVencerDay(List<CmtTipoSolicitudMgl> tipoSolicitudList) throws ApplicationException;

    /**
     * Cuenta las Solicitudes vencidas en la fecha actual. Permite realizar el
     * conteo de las solicitudes por tipos y creadas en la fecha actual.
     *
     * @author Johnnatan Ortiz
     * @param tipoSolicitudList lista de los tipos de solicitudes a consultar
     * @return solicitudes
     * @throws ApplicationException
     */
    public int getCountAllSolicitudVencidasDay(List<CmtTipoSolicitudMgl> tipoSolicitudList) throws ApplicationException;

    /**
     * Gestiona una solicitud VT. Permite realizar la gestion de una solicitud
     * de VT.
     *
     * @author Johnnatan Ortiz
     * @param solicitudCm solicitud VT a gestionar
     * @return solicitud gestionada
     * @throws ApplicationException
     */
    public CmtSolicitudCmMgl gestionSolicitudVt(CmtSolicitudCmMgl solicitudCm)
            throws ApplicationException;

    /**
     * Bloquea o Desbloquea una solicitud. Permite realizar el bloqueo o
     * desbloqueo de una solicitud en el repositorio para la gestion.
     *
     * @author Johnnatan Ortiz
     * @param solicitudCm solicitud a bloquear o desbloquear
     * @param bloqueo si es bloqueo o no
     * @return solicitud
     * @throws ApplicationException
     */
    public CmtSolicitudCmMgl bloquearDesbloquearSolicitud(CmtSolicitudCmMgl solicitudCm,
            boolean bloqueo) throws ApplicationException;

    public List<AuditoriaDto> construirAuditoria(CmtSolicitudCmMgl cmtSolicitudCmMgl)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;

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
    public CmtSolicitudCmMgl gestionSolicitudHhpp(CmtSolicitudCmMgl solicitudCm, String usuario, int perfil) throws ApplicationException;

    /**
     * Guarda la gestion de HHPP
     *
     * @author Carlos villamil
     * @param sol solicitud crea CM
     * @param cdsm direccion principal
     * @return 
     * @throws ApplicationException
     */
    public int validaLongitudDireccion(CmtSolicitudCmMgl sol, CmtDireccionSolicitudMgl cdsm) throws ApplicationException;

    /**
     * M&eacute;todo para contar las solicitudes de eliminacion existentes en la base de datos
     * 
     * @param cuentaMatrizId Identificador de la cuetna matriz asociada a la solicitud
     * @return {@link Integer} cantidad de registros existentes en la base de datos
     * @throws ApplicationException 
     */
    public Integer cantidadSolicitudesEliminacionPorCuentaMatriz(BigDecimal cuentaMatrizId) throws ApplicationException;

   /**
     * Obtiene las solicitudes finalizadas o no segun un periodo, estado y por
     * tipo de solicitud
     *
     * @author Lenis Cardenas
     * @param tipoReporte
     * @param cmtTipoSolicitudMgl
     * @param estado
     * @param fechaFin
     * @param fechaInicio
     * @param usuario
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */
    public void getSolicitudesDetallado(String tipoReporte, CmtTipoSolicitudMgl cmtTipoSolicitudMgl, Date fechaInicio, Date fechaFin, BigDecimal estado, String usuario) throws ApplicationException;
   
    
    public boolean validarExisteSolCMTipo(CmtCuentaMatrizMgl cmtCuentaMatrizMgl, CmtSolicitudCmMgl t)  throws ApplicationException;
    
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
    public CmtSolicitudCmMgl findBySolCMTipoSol(CmtCuentaMatrizMgl cuentaMatrizId, 
            CmtTipoSolicitudMgl cmtTipoSolicitudMgl, CmtBasicaMgl estadoSol) throws ApplicationException;
    
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
    public int generarReporteDetalladoContar(String tipoReporte, CmtTipoSolicitudMgl cmtTipoSolicitudMgl,
            Date fechaInicio, Date fechaFin, BigDecimal estado, long page, int numeroRegistros, String usuario)
            throws ApplicationException;
      
       
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
    public FiltroReporteSolicitudCMDto getReporteGeneralSolicitudesSearchFinalCon(String tipoReporte,
            BigDecimal cmtTipoSolicitudMgl, Date fechaInicio, Date fechaFin,
            BigDecimal estado,int firstResult, int maxResults)
            throws ApplicationException;
    
    
    
    public CmtSolicitudCmMgl updateCm(CmtSolicitudCmMgl t) throws ApplicationException;
    
    
    /**
     * Obtiene una Solicitud por rol
     *
     * @author Victor Bocanegra
     * @param tipoSolicitudList lista de los tipos de solicitudes a consultar
     * @param solicitudId id de la solicitud a consultar
     * @return CmtSolicitudCmMgl
     * @throws ApplicationException
     */
    public CmtSolicitudCmMgl findBySolicitudPorPermisos(
            List<CmtTipoSolicitudMgl> tipoSolicitudList,
            BigDecimal solicitudId) throws ApplicationException;

    public void envioCorreoGestion(CmtSolicitudCmMgl solicitud);
    
  public List<CmtReporteDetalladoDto> getSolicitudesSearchDetalle(String tipoReporte,
            CmtTipoSolicitudMgl cmtTipoSolicitudMgl, Date fechaInicio, Date fechaFin,
            BigDecimal estado, int inicio, int fin, String usuario,
            int procesados, int regProcesar) throws ApplicationException;

}
