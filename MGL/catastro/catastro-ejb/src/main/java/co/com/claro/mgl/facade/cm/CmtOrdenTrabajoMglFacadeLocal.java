package co.com.claro.mgl.facade.cm;

import co.claro.wcc.services.search.searchcuentasmatrices.SearchCuentasMatricesFault;
import co.claro.wcc.services.upload.uploadcuentasmatrices.UploadCuentasMatricesFault;
import co.com.claro.cmas400.ejb.respons.ResponseOtEdificiosList;
import co.com.claro.mgl.dtos.CmtFiltroConsultaOrdenesDto;
import co.com.claro.mgl.dtos.CmtOnyxResponseDto;
import co.com.claro.mgl.dtos.ReporteHistoricoOtDIRDto;
import co.com.claro.mgl.dtos.ReporteOtCMDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.OtHhppMgl;
import co.com.claro.mgl.jpa.entities.TipoOtHhppMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtAgendamientoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtComunidadRr;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtEstadoIntxExtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtEstadoxFlujoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoAuditoriaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtRegionalRr;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoOtMgl;
import com.amx.service.exp.operation.mernotify.v1.MERNotifyResponseType;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.xml.datatype.DatatypeConfigurationException;
import org.primefaces.model.file.UploadedFile;


/**
 *
 * @author ortizjaf
 */
public interface CmtOrdenTrabajoMglFacadeLocal {

    CmtOrdenTrabajoMgl crearOt(CmtOrdenTrabajoMgl ot) throws ApplicationException;

    CmtOrdenTrabajoMgl actualizarOt(CmtOrdenTrabajoMgl ot, BigDecimal nuevoEstado) throws ApplicationException;

    CmtOrdenTrabajoMgl findOtById(BigDecimal idOt) throws ApplicationException;

    List<CmtOrdenTrabajoMgl> findByIdCm(CmtCuentaMatrizMgl cuentaMatrizMgl) throws ApplicationException;

    boolean validaEstadoCm(CmtTipoOtMgl tipoOt, CmtCuentaMatrizMgl cuentaMatrizMgl) throws ApplicationException;

    boolean validaProcesoOt(CmtOrdenTrabajoMgl ot, String proceso) throws ApplicationException;

    void setUser(String mUser, int mPerfil) throws ApplicationException;

    List<CmtOrdenTrabajoMgl> findPaginacion(CmtCuentaMatrizMgl cmtCuentaMatrizMgl)
            throws ApplicationException;

    /**
     * Busca las Ordenes de Trabajo por torres
     *
     * @author cardenaslb
     * @param cmtSubEdificioMgl
     * @return Lista de Ordenes de trabajo por SubEdificios
     * @throws ApplicationException
     */
    List<CmtOrdenTrabajoMgl> findPaginacionSub(CmtSubEdificioMgl cmtSubEdificioMgl)
            throws ApplicationException;

    Long countByCm(CmtCuentaMatrizMgl cmtCuentaMatrizMgl) throws ApplicationException;

    /**
     * Cuenta la cantida de ordenes por Subedificio
     *
     * @author cardenaslb
     * @param cmtSubEdificioMgl
     * @return Lista de Ordenes de trabajo por SubEdificios
     * @throws ApplicationException
     */
    Long countBySub(CmtSubEdificioMgl cmtSubEdificioMgl) throws ApplicationException;

    List<AuditoriaDto> construirAuditoria(CmtOrdenTrabajoMgl cmtOrdenTrabajoMgl)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;

    /**
     * Busca las Ordenes de Trabajo por estados X flujo.Permite realizar la
     * busqueda de las Ordenes de Trabajo por medio de los estado X flujo,
     * comunidad, division y tipo.
     *
     * @author Johnnatan Ortiz
     * @param firstResult pagina de la busqueda
     * @param maxResults maximo numero de resultados
     * @param estadosxflujoList lista de estado X flujo
     * @param filtro filtro para la consulta
     * @return Ordenes de Trabajo encontrada en el repositorio
     * @throws ApplicationException
     */
    List<CmtOrdenTrabajoMgl> findByFiltroParaGestionPaginacion(
            List<BigDecimal> estadosFLujoUsuario, int paginaSelected, int maxResults,
            CmtFiltroConsultaOrdenesDto filtro) throws ApplicationException;

      /**
     * Cuenta las Ordenes de Trabajo por estados X flujo.Permite realizar el
     * conteo de las Ordenes de Trabajo por medio de los estado X flujo,
     * comunidad, division y tipo.
     *
     * @author Johnnatan Ortiz
     * @param estadosxflujoList lista de estado X flujo
     * @param filtro para la consulta
     * @return Ordenes de Trabajo encontrada en el repositorio
     * @throws ApplicationException
     */
    int getCountByFiltroParaGestion(List<BigDecimal> estadosFLujoUsuario,
            CmtFiltroConsultaOrdenesDto filtro) throws ApplicationException;

    /**
     * Busca los estados a los cuales tiene acceso un usuario. Permite realizar
     * la busqueda de los estado dentro de los flujos de los tipo de ordenes de
     * trabajo a los cuales tiene acceso un usuario.
     *
     * @author Johnnatan Ortiz
     * @param facesContext faces Context
     * @return lista de id de estados a los cuales tiene acceso el usuario
     * @throws ApplicationException
     */
    public List<BigDecimal> getEstadosFLujoUsuario(FacesContext facesContext) throws ApplicationException;

    /**
     * M&eacute;todo para consultar ordenes de trabajo abiertas
     *
     * @param cuentaMatriz {@link CmtCuentaMatrizMgl} CM a la que esta asociada
     * la OT
     * @return {@link List}&lt{@link CmtOrdenTrabajoMgl}> Ordenes de trabajo
     * encontradas
     * @throws ApplicationException Excepci&oacute;n lanzada por la consulta
     */
    List<CmtOrdenTrabajoMgl> ordenesTrabajoActivas(CmtCuentaMatrizMgl cuentaMatriz) throws ApplicationException;

    /**
     * Metodo para consultar ordenes de trabajo de vt cerradas Autor: Victor
     * Bocanegra
     *
     * @param cuentaMatriz {@link CmtCuentaMatrizMgl} CM a la que esta asociada
     * la OT
     * @return CmtOrdenTrabajoMgl Orden de trabajo encontrada
     * @throws ApplicationException Excepci&oacute;n lanzada por la consulta
     */
    CmtOrdenTrabajoMgl ordenesTrabajoVtCerradas(CmtCuentaMatrizMgl cuentaMatriz) throws ApplicationException;

    /**
     * Metodo para cargar un documento al UCM Autor: Victor Bocanegra
     *
     * @param ordenTrabajoMgl a la cual se le van adjuntar los documentos
     * @param archivo archivo que se va a cargar al UCM
     * @param usuario que realiza la operacion
     * @param perfil del usuario que realiza la operacion
     * @param origenArchivo origen del archivo
     * @return boolean
     * @throws java.net.MalformedURLException
     * @throws ApplicationException Excepcion lanzada por la carga del archivo
     * @throws java.io.FileNotFoundException
     * @throws com.amx.service.documentucm.v1.ClaroFault
     */
    public boolean cargarArchivoCierreComercial(CmtOrdenTrabajoMgl ordenTrabajoMgl,
            UploadedFile archivo, String usuario, int perfil, String origenArchivo)
            throws MalformedURLException, FileNotFoundException, ApplicationException,
            SearchCuentasMatricesFault, UploadCuentasMatricesFault;

    /**
     * Metodo para obtener los documentos en el UCM de una OT Autor: Victor
     * Bocanegra
     *
     * @param ordenTrabajoMgl a la cual se le van adjuntar los documentos
     * @return List<String>
     * @throws javax.xml.datatype.DatatypeConfigurationException
     * @throws ApplicationException Excepcion lanzada por la consulta
     * @throws java.io.FileNotFoundException
     * @throws com.amx.service.documentucm.v1.ClaroFault
     */
    public List<String> obtenerArchivosCierreComercial(CmtOrdenTrabajoMgl ordenTrabajoMgl)
            throws DatatypeConfigurationException, FileNotFoundException,
            ApplicationException, SearchCuentasMatricesFault;

    /**
     * Actualiza una Orden de Trabajo. Permite realizar la actualizacion de una
     * Orden de Trabajo proceso cierre comercial en el repositorio.
     *
     * @author Victor Bocanegra
     * @param ot Orden de Trabajo a actualizar en el repositorio
     * @param nuevoEstado estado interno cierre comercial
     * @return Orden de Trabajo actualizada en el repositorio
     * @throws ApplicationException
     */
    public CmtOrdenTrabajoMgl actualizarOtCierreComercial(CmtOrdenTrabajoMgl ot,
            BigDecimal nuevoEstado) throws ApplicationException;

    /**
     * Busca las Ordenes de Trabajo asociadas a un tipo de trabajo.
     *
     * @author Victor Bocanegra
     * @param tipoOtMgl tipo de trabajo
     * @return Ordenes de Trabajo encontrada en el repositorio asociadas a un
     * tipo de trabajo
     * @throws ApplicationException
     */
    public List<CmtOrdenTrabajoMgl> findByTipoTrabajo(CmtTipoOtMgl tipoOtMgl) throws ApplicationException;

    /**
     * Actualiza el estado de la Ot de acometida y sus subedificios asociados
     * despues de la creacion.
     *
     * @author Victor Bocanegra
     * @param ot de acometida
     * @param usuario en sesion
     * @param perfil del usuario
     * @throws ApplicationException
     */
    public void changeStatusCmByFlowAco(CmtOrdenTrabajoMgl ot, String usuario, int perfil)
            throws ApplicationException;

    /**
     * Busca una Orden de Trabajo por ID. Permite realizar la busqueda de una
     * Orden de Trabajo por su ID en el repositorio.
     *
     * @author Victor Bocanegra
     * @param idOt ID de la Orden de Trabajo a buscar en el repositorio
     * @param estadosxflujoList lista de estados por flujos a la cual tiene
     * acceso el usuario en sesion
     * @return Orden de Trabajo encontrada en el repositorio
     * @throws ApplicationException
     */
    CmtOrdenTrabajoMgl findOtByIdAndPermisos(BigDecimal idOt, List<BigDecimal> estadosxflujoList)
            throws ApplicationException;

   /**
     * Busca las Ordenes de Trabajo por estados X flujo.Permite realizar la
     * busqueda de las Ordenes de Trabajo por medio de los estado X flujo,
     * comunidad, division y tipo.
     *
     * @author Victor Bocanegra
     * @param estadosxflujoList lista de estado X flujo
     * @param filtro para la consulta
     * @return Ordenes de Trabajo encontrada en el repositorio
     * @throws ApplicationException
     */
    List<CmtOrdenTrabajoMgl> findByFiltroParaGestionExportarOt(
            List<BigDecimal> estadosxflujoList, CmtFiltroConsultaOrdenesDto filtro) 
            throws ApplicationException;

    /**
     * Actualiza una Orden de Trabajo. Permite realizar la actualizacion de una
     * Orden de Trabajo
     *
     * @author Victor Bocanegra
     * @param ot Orden de Trabajo a actualizar en el repositorio
     * @param usuario que actualiza
     * @param perfil del usuario
     * @return Orden de Trabajo actualizada en el repositorio
     * @throws ApplicationException
     */
    CmtOrdenTrabajoMgl actualizarOtCcmm(CmtOrdenTrabajoMgl ot,
            String usuario, int perfil) throws ApplicationException;

    /**
     *
     * @param ot
     * @param estadoFlujo
     * @param usuario
     * @param perfil
     * @return
     * @throws ApplicationException
     */
    String crearOtRRporAgendamiento(CmtOrdenTrabajoMgl ot,
            CmtEstadoxFlujoMgl estadoFlujo, String usuario, int perfil) throws ApplicationException;

    /**
     * Busca las Ordenes de Trabajo por estados X flujo.Permite realizar la
     * busqueda de las Ordenes de Trabajo que generan OTs de acometidas por
     * medio de los estado X flujo, comunidad, division y tipo.
     *
     * @author Victor Bocanegra
     * @param estadosxflujoList lista de estado X flujo
     * @param filtro para la consulta
     * @param tiposOtgeneranAco tipos de Ot que generan acometidas
     * @return Ordenes de Trabajo encontrada en el repositorio
     * @throws ApplicationException
     */
    List<CmtOrdenTrabajoMgl> findByOtCloseForGenerarAcometida(List<BigDecimal> estadosxflujoList,
            CmtFiltroConsultaOrdenesDto filtro, List<CmtTipoOtMgl> tiposOtgeneranAco, 
            int paginaSelected, int maxResults) throws ApplicationException;

    /**
     * Cuenta las Ordenes de Trabajo por estados X flujo.Permite contar la las
     * Ordenes de Trabajo que generan OTs de acometidas por medio de los estado
     * X flujo, comunidad, division y tipo.
     *
     * @author Victor Bocanegra
     * @param estadosxflujoList lista de estado X flujo
     * @param filtro para la consulta
     * @param tiposOtgeneranAco tipos de Ot que generan acometidas
     * @return Ordenes de Trabajo encontrada en el repositorio
     * @throws ApplicationException
     */
    int getCountOtCloseForGenerarAcometida(List<BigDecimal> estadosxflujoList,
            CmtFiltroConsultaOrdenesDto filtro, List<CmtTipoOtMgl> tiposOtgeneranAco) throws ApplicationException;

    /**
     * Busca una Orden de Trabajo por estados X flujo.
     *
     * @author Victor Bocanegra
     * @param estadosxflujoList lista de estado X flujo
     * @param idOt id de la Ot a buscar
     * @param tiposOtgeneranAco tipos de Ot que generan acometidas
     * @param conPermisos consulta con roles
     * @return CmtOrdenTrabajoMgl encontrada en el repositorio
     * @throws ApplicationException
     */
    CmtOrdenTrabajoMgl findIdOtCloseForGenerarAcometidaAndPermisos(List<BigDecimal> estadosxflujoList,
            List<CmtTipoOtMgl> tiposOtgeneranAco, BigDecimal idOt, boolean conPermisos)
            throws ApplicationException;

    /**
     * Elimina una orden de trabajo de rr.
     *
     * @author Victor Bocanegra
     * @param agenda con la informacion de la ot
     * @param usuario que realiza la transacion
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    boolean eliminarOtRRporAgendamiento(CmtAgendamientoMgl agenda, String usuario)
            throws ApplicationException;

    /**
     * Metodo para consultar los trabajos que tiene una CM en RR
     *
     * @author Victor bocanegra
     * @param numeroCuenta cnumero de la cuenta matriz
     * @return ResponseOtEdificiosList encontrado
     * @throws ApplicationException
     */
    ResponseOtEdificiosList ordenTrabajoEdificioQuery(String numeroCuenta)
            throws ApplicationException;

    /**
     * Metodo para consumir el nuevo servicio de consulta a una OT hija en onix
     *
     * @author Victor bocanegra
     * @param numeroOtHija numero de la ot hija a consultar
     * @return ResponseOtEdificiosList encontrado
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    CmtOnyxResponseDto consultarOTHijaOnyx(String numeroOtHija) throws ApplicationException;

    /**
     * Crea y actualiza una orden en rr
     *
     * @author Victor Bocanegra
     * @param numeroCuentaParametro
     * @param tipoOtHhppMgl
     * @param usuario
     * @param ot
     * @return
     * @throws ApplicationException
     */
      String crearOtRRporAgendamientoHhpp(String numeroCuentaParametro,
            TipoOtHhppMgl tipoOtHhppMgl, String usuario, OtHhppMgl ot) throws ApplicationException; 
      
              /**
     * Elimina una orden de trabajo de rr.
     *
     * @author Victor Bocanegra
     * @param numeroCuentaParametro
     * @param numeroOt numero de ot a eliminar
     * @param usuario que realiza la transacion
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    boolean ordenTrabajoEdificioDeleteHhpp(String numeroCuentaParametro, String numeroOt, String usuario)
            throws ApplicationException;

    /**
     * Obtiene consulta para reporte de ordenes.
     *
     * @author cardenaslb
     * @param params
     * @param usuario que realiza la transacion
     * @param listaBasicaDir
     * @param listaBasicaCm
     * @param listacmtRegionalMgl
     * @param listacmtComunidadRr
     * @param listaEstadosIntExt
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    
     List<ReporteOtCMDto>  findReporteOtCm(HashMap<String, Object> params, String usuario,  List<CmtBasicaMgl> listaBasicaDir,
     List<CmtBasicaMgl> listaBasicaCm,
     List<CmtRegionalRr> listacmtRegionalMgl,
     List<CmtComunidadRr> listacmtComunidadRr,
     List<CmtEstadoIntxExtMgl> listaEstadosIntExt)
            throws ApplicationException;

    /**
     * Obtiene el numero total de registros del reporte de ordenes.
     *
     * @author cardenaslb
     * @param params
     * @param usuario que realiza la transacion
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    int findReporteOtCmTotal(HashMap<String, Object> params, String usuario) throws ApplicationException;

    /**
     * Obtiene los registros del reporte Historico de ordenes.
     *
     * @author cardenaslb
     * @param params
     * @param usuario que realiza la transacion
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */

    public void findReporteHistoricoOtCm(HashMap<String, Object> params, String usuario) throws ApplicationException;

    /**
     * Conteo de los registros del reporte Historico de ordenes.
     *
     * @author cardenaslb
     * @param params
     * @param usuario que realiza la transacion
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public int findReporteHistoricoOtCmTotal(HashMap<String, Object> params, String usuario) throws ApplicationException;

    /**
     * Obtiene los registros del Reporte de Ordenes de direcciones
     *
     * @author cardenaslb
     * @param params
     * @param usuario que realiza la transacion
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void findReporteOtDIR(HashMap<String, Object> params, String usuario) throws ApplicationException;

    /**
     * Obtiene el numero total de registros del reporte de ordenes.
     *
     * @author cardenaslb
     * @param params
     * @param usuario que realiza la transacion
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    int findReporteOtDIRTotal(HashMap<String, Object> params, String usuario) throws ApplicationException;

    /**
     * Obtiene los registros del reporte de ordenes de direcciones.
     *
     * @author cardenaslb
     * @param params
     * @param usuario que realiza la transacion
     * @param listaBasicaDir
     * @param listaBasicaCm
     * @param listacmtRegionalMgl
     * @param listacmtComunidadRr
     * @param listaEstadosIntExt
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    
     public List<ReporteHistoricoOtDIRDto>  findReporteHistoricoOtDIR(HashMap<String, Object> params, String usuario, List<CmtBasicaMgl> listaBasicaDir,
            List<CmtBasicaMgl> listaBasicaCm,
            List<CmtRegionalRr> listacmtRegionalMgl,
            List<CmtComunidadRr> listacmtComunidadRr,
            List<CmtEstadoIntxExtMgl> listaEstadosIntExt) throws ApplicationException ;
     
            /**
     * Obtiene el numero total de registros de la consulta de ordenes de direcciones.
     *
     * @author cardenaslb
     * @param params
     * @param usuario que realiza la transacion
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public int findReporteHistoricoOtDIRTotal(HashMap<String, Object> params, String usuario) throws ApplicationException;

    /**
     * Busqueda el ultimo estado de la orden en auditoria con estado razonado
     *
     * @author victor bocanegra
     * @param ot valor de la cuenta matriz para la busqueda
     * @param estadoIntActual valor de la basica de tecnologia para la busqueda
     * @return CmtOrdenTrabajoAuditoriaMgl
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    CmtOrdenTrabajoAuditoriaMgl findUltimoEstadoOtRazonada(CmtOrdenTrabajoMgl ot,
            BigDecimal estadoIntActual) throws ApplicationException;

    /**
     * Actualiza con OK la ot en RR
     *
     * @author victor bocanegra
     * @param ot orden de trabajo
     * @param numeroOT numero de laorden en RR
     * @param estadoFlujo de la orden
     * @param usuario
     * @param perfil
     * @return true or false
     */
    boolean cerrarOTRR(CmtOrdenTrabajoMgl ot, String numeroOT,
            CmtEstadoxFlujoMgl estadoFlujo, String usuario, int perfil) throws ApplicationException;

    /**
     * Actualiza la una ot en RR creada desde direcciones
     *
     * @author victor bocanegra
     * @param numeroCuentaParametro numero de la cuenta a la que esta asociada
     * la orden
     * @param numeroOT numero de la orden en RR
     * @param usuario
     * @param tipoOtHhppMgl
     * @param isIncial tipo de cambio de estado
     * @param ot  orden de direcciones
     * @param notaCierre nota de cierre de OT en RR.
     * @return true or false
     */
    boolean actualizarEstadoResultadoOTRRHhpp(String numeroCuentaParametro, String numeroOt,
            String usuario, TipoOtHhppMgl tipoOtHhppMgl, boolean isIncial,
            OtHhppMgl ot, String notaCierre) throws ApplicationException;
    
        /**
     * Bloquea o Desbloquea una orden. Permite realizar el bloqueo o
     * desbloqueo de una orden en el repositorio para la gestion.
     *
     * @author Victor Bocanegra
     * @param orden orden a bloquear o desbloquear
     * @param bloqueo si es bloqueo o no
     * @return CmtOrdenTrabajoMgl
     * @throws ApplicationException
     */
    CmtOrdenTrabajoMgl bloquearDesbloquearOrden(CmtOrdenTrabajoMgl orden,
            boolean bloqueo) throws ApplicationException;
    
    /**
     * Metodo para notificar una orden de trabajo en ONIX
     *
     * @author Victor Bocanegra
     * @param orden orden a notificar
     * @param numOtHija numero de la Ot hija
     * @return MERNotifyResponseType
     * @throws ApplicationException
     */
    MERNotifyResponseType notificarOrdenOnix(CmtOrdenTrabajoMgl orden, String numOtHija)
            throws ApplicationException;


    void eliminarOTByCM (BigDecimal idCuentaMatriz) throws ApplicationException;
    
    int countByFiltroParaGestionExportarOt(
            List<BigDecimal> estadosxflujoList, BigDecimal gpoDepartamento,
            BigDecimal gpoCiudad, BigDecimal idTipoOt, BigDecimal estadoInternoBasicaId,
            BigDecimal tecnologia,String codigoReg) throws ApplicationException;
    
    List<CmtOrdenTrabajoMgl> findOtAcometidaSubEdificio(CmtSubEdificioMgl cmtSubEdificioMgl)
            throws ApplicationException;
    
 
}
