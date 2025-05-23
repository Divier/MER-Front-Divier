package co.com.claro.mgl.facade.cm;

import co.claro.wcc.services.search.searchcuentasmatrices.SearchCuentasMatricesFault;
import co.claro.wcc.services.upload.uploadcuentasmatrices.UploadCuentasMatricesFault;
import co.com.claro.cmas400.ejb.respons.ResponseOtEdificiosList;
import co.com.claro.mgl.businessmanager.cm.CmtOrdenTrabajoMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtOrdenTrabajoReportThreadManager;
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
import co.com.claro.mgl.utils.ClassUtils;
import com.amx.service.exp.operation.mernotify.v1.MERNotifyResponseType;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.xml.datatype.DatatypeConfigurationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.model.file.UploadedFile;
import co.com.claro.mgl.dtos.CmtFiltroConsultaOrdenesDto;


/**
 * Facade Orden de Trabajo. Expone la logica de negocio para el manejo de
 * ordenes de trabajo en el repositorio.
 *
 * @author Johnnatan Ortiz
 * @version 1.00.000
 */
@Stateless
public class CmtOrdenTrabajoMglFacade implements CmtOrdenTrabajoMglFacadeLocal {
    
    private static final Logger LOGGER = LogManager.getLogger(CmtOrdenTrabajoMglFacade.class);
    
    private String user = "";
    private int perfil = 0;

    /**
     * Crea una Orden de Trabajo. Permite realizar la creacion de una Orden de
     * Trabajo en el repositorio.
     *
     * @author Johnnatan Ortiz
     * @param ot Orden de Trabajo a crear en el repositorio
     * @return Orden de Trabajo creada en el repositorio
     * @throws ApplicationException
     */
    @Override
    public CmtOrdenTrabajoMgl crearOt(CmtOrdenTrabajoMgl ot) throws ApplicationException {
        CmtOrdenTrabajoMglManager manager = new CmtOrdenTrabajoMglManager();
        return manager.crearOt(ot, user, perfil);
    }

    /**
     * Actualiza una Orden de Trabajo.Permite realizar la actualizacion de una
 Orden de Trabajo en el repositorio.
     *
     * @author Johnnatan Ortiz
     * @param ot Orden de Trabajo a actualizar en el repositorio
     * @param nuevoEstado
     * @return Orden de Trabajo actualizada en el repositorio
     * @throws ApplicationException
     */
    @Override
    public CmtOrdenTrabajoMgl actualizarOt(CmtOrdenTrabajoMgl ot, BigDecimal nuevoEstado) throws ApplicationException {
        CmtOrdenTrabajoMglManager manager = new CmtOrdenTrabajoMglManager();
        try {
            return manager.actualizarOt(ot, nuevoEstado, user, perfil);
        } catch (ApplicationException ex) {
            LOGGER.error(ex.getMessage());
            throw ex;
        }
    }

    /**
     * Busca una Orden de Trabajo por ID. Permite realizar la busqueda de una
     * Orden de Trabajo por su ID en el repositorio.
     *
     * @author Johnnatan Ortiz
     * @param idOt ID de la Orden de Trabajo a buscar en el repositorio
     * @return Orden de Trabajo encontrada en el repositorio
     * @throws ApplicationException
     */
    @Override
    public CmtOrdenTrabajoMgl findOtById(BigDecimal idOt) throws ApplicationException {
        CmtOrdenTrabajoMglManager manager = new CmtOrdenTrabajoMglManager();
        return manager.findOtById(idOt);
    }

    /**
     * Busca las Ordenes de Trabajo asociadas a una CM. Permite realizar la
     * busqueda de las Ordenes de Trabajo asociadas a una Cuenta Matriz en el
     * repositorio por el ID de la Cuenta Matriz.
     *
     * @author Johnnatan Ortiz
     * @param cuentaMatrizMgl Cuenta Matriz
     * @return Ordenes de Trabajo encontrada en el repositorio asociadas a una
     * Cuenta Matriz
     * @throws ApplicationException
     */
    @Override
    public List<CmtOrdenTrabajoMgl> findByIdCm(CmtCuentaMatrizMgl cuentaMatrizMgl) throws ApplicationException {
        CmtOrdenTrabajoMglManager manager = new CmtOrdenTrabajoMglManager();
        return manager.findByIdCm(cuentaMatrizMgl);
    }

    /**
     * Valida el estado de la CM para la creacion de la OT. Permite validar el
     * estado actual de la cuenta matriz para la creacion de un Orden de
     * trabajo.
     *
     * @author Johnnatan Ortiz
     * @param tipoOt Tipo de Orden de trabajo
     * @param cuentaMatrizMgl Cuenta matriz sobre la cual se quiere crear la OT
     * @return si el estado actual de la CM permite la creacion de la OT
     * @throws ApplicationException
     */
    @Override
    public boolean validaEstadoCm(CmtTipoOtMgl tipoOt, CmtCuentaMatrizMgl cuentaMatrizMgl) throws ApplicationException {
        CmtOrdenTrabajoMglManager manager = new CmtOrdenTrabajoMglManager();
        return manager.validaEstadoCm(tipoOt, cuentaMatrizMgl);
    }

    /**
     * Valida Si se debe habilitar algun proceso sobre la OT. Permite validar si
     * para el estado actual de una OT se debe habilitar algun proceso, puede
     * ser habilitar alguna pestana de la OT o del Formulario de visita Tecnica
     * o algun proceso sobre RR.
     *
     * @author Johnnatan Ortiz
     * @param ot Tipo de Orden de trabajo
     * @param proceso Cuenta matriz sobre la cual se quiere crear la OT
     * @return si debe habiliar el formulario
     * @throws ApplicationException
     */
    @Override
    public boolean validaProcesoOt(CmtOrdenTrabajoMgl ot, String proceso)
            throws ApplicationException {
        CmtOrdenTrabajoMglManager manager = new CmtOrdenTrabajoMglManager();
        return manager.validaProcesoOt(ot, proceso);
    }

    @Override
    public void setUser(String mUser, int mPerfil) throws ApplicationException {
        if (mUser == null || mUser.isEmpty() || mPerfil == 0) {
            throw new ApplicationException("El Usuario o perfil Nopueden ser nulos");
        }
        user = mUser;
        perfil = mPerfil;
    }

    @Override
    public List<CmtOrdenTrabajoMgl> findPaginacion( CmtCuentaMatrizMgl cmtCuentaMatrizMgl )
            throws ApplicationException {
        CmtOrdenTrabajoMglManager manager = new CmtOrdenTrabajoMglManager();
        return manager.findPaginacion( cmtCuentaMatrizMgl );
    }

    @Override
    public Long countByCm(CmtCuentaMatrizMgl cmtCuentaMatrizMgl)
            throws ApplicationException {
        CmtOrdenTrabajoMglManager manager = new CmtOrdenTrabajoMglManager();
        return manager.countByCm(cmtCuentaMatrizMgl);
    }

    /**
     * cuenta las ordenes d trabajo asociadas a las Subedificios
     *
     * @author cardenaslb
     * @return las ordenes de trabajo asociados a los Subedificios
     * @throws ApplicationException
     */
    @Override
    public Long countBySub(CmtSubEdificioMgl cmtSubEdificioMgl)
            throws ApplicationException {
        CmtOrdenTrabajoMglManager manager = new CmtOrdenTrabajoMglManager();
        return manager.countBySub(cmtSubEdificioMgl);
    }

    @Override
    public List<AuditoriaDto> construirAuditoria(CmtOrdenTrabajoMgl cmtOrdenTrabajoMgl)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (cmtOrdenTrabajoMgl != null && cmtOrdenTrabajoMgl.getIdOt()!=null) {
            CmtOrdenTrabajoMglManager cmtOrdenTrabajoMglManager = new CmtOrdenTrabajoMglManager();
            return cmtOrdenTrabajoMglManager.construirAuditoria(cmtOrdenTrabajoMgl);
        } else {
            return null;
        }
    }

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
    @Override
    public List<CmtOrdenTrabajoMgl> findByFiltroParaGestionPaginacion(
            List<BigDecimal> estadosFLujoUsuario, int paginaSelected, int maxResults,
            CmtFiltroConsultaOrdenesDto filtro) throws ApplicationException {
        CmtOrdenTrabajoMglManager cmtOrdenTrabajoMglManager = new CmtOrdenTrabajoMglManager();
        return cmtOrdenTrabajoMglManager.findByFiltroParaGestionPaginacion(estadosFLujoUsuario,
                paginaSelected, maxResults, filtro);
    }

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
    @Override
    public int getCountByFiltroParaGestion(List<BigDecimal> estadosFLujoUsuario,
            CmtFiltroConsultaOrdenesDto filtro) throws ApplicationException {
        CmtOrdenTrabajoMglManager cmtOrdenTrabajoMglManager = new CmtOrdenTrabajoMglManager();
        return cmtOrdenTrabajoMglManager.getCountByFiltroParaGestion(estadosFLujoUsuario,filtro);
    }

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
    @Override
    public List<BigDecimal> getEstadosFLujoUsuario(FacesContext facesContext)
            throws ApplicationException {
        CmtOrdenTrabajoMglManager cmtOrdenTrabajoMglManager = new CmtOrdenTrabajoMglManager();
        return cmtOrdenTrabajoMglManager.getEstadosFLujoUsuario(facesContext);
    }

    /**
     * Busca las ordenes de trabajo asociados a los Subedificios
     *
     * @author cardenaslb
     * @return las ordenes de trabajo asociados a los Subedificios
     * @throws ApplicationException
     */
    @Override
    public List<CmtOrdenTrabajoMgl> findPaginacionSub( CmtSubEdificioMgl cmtSubEdificioMgl) 
            throws ApplicationException {
        CmtOrdenTrabajoMglManager manager = new CmtOrdenTrabajoMglManager();
        return manager.findPaginacionSub( cmtSubEdificioMgl);
    }

    /**
     * {@inheritDoc }
     *
     * @param cuentaMatriz {@link CmtCuentaMatrizMgl} CM a la que esta asociada la OT
     * @return {@link List}&lt{@link CmtOrdenTrabajoMgl}> Ordenes de trabajo encontradas
     * @throws ApplicationException Excepci&oacute;n lanzada por la consulta
     */
    @Override
    public List<CmtOrdenTrabajoMgl> ordenesTrabajoActivas(CmtCuentaMatrizMgl cuentaMatriz) throws ApplicationException {
        CmtOrdenTrabajoMglManager manager = new CmtOrdenTrabajoMglManager();
        return manager.ordenesTrabajoActivas(cuentaMatriz);
    }
    
        
   /**
     * Metodo para consultar ordenes de trabajo de vt cerradas
     * Autor: Victor Bocanegra
     * @param cuentaMatriz {@link CmtCuentaMatrizMgl} CM a la que esta asociada la OT
     * @return CmtOrdenTrabajoMgl Orden de trabajo encontrada
     * @throws ApplicationException Excepci&oacute;n lanzada por la consulta
     */
    @Override
    public CmtOrdenTrabajoMgl ordenesTrabajoVtCerradas
            (CmtCuentaMatrizMgl cuentaMatriz) throws ApplicationException {
        
        CmtOrdenTrabajoMglManager manager = new CmtOrdenTrabajoMglManager();
        return manager.ordenesTrabajoVtCerradas(cuentaMatriz);
    }
    
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
     * @throws java.io.FileNotFoundException
     * @throws ApplicationException Excepcion lanzada por la carga del archivo
     * @throws com.amx.service.documentucm.v1.ClaroFault
     */
    @Override
    public boolean cargarArchivoCierreComercial(CmtOrdenTrabajoMgl ordenTrabajoMgl,
            UploadedFile archivo, String usuario, int perfil, String origenArchivo)
            throws  MalformedURLException, FileNotFoundException, ApplicationException,
            SearchCuentasMatricesFault, UploadCuentasMatricesFault{
        CmtOrdenTrabajoMglManager manager = new CmtOrdenTrabajoMglManager();
        return manager.cargarArchivoCierreComercial(ordenTrabajoMgl, archivo, usuario, perfil, origenArchivo);
        
    }

        /**
     * Metodo para obtener los documentos en el UCM de una OT
     * Autor: Victor Bocanegra
     * @param ordenTrabajoMgl  a la cual se le van adjuntar los documentos
     * @return List<String>
     * @throws ApplicationException Excepcion lanzada por la consulta
     * @throws java.io.FileNotFoundException
     * @throws com.amx.service.documentucm.v1.ClaroFault
     */
    @Override
    public List<String> obtenerArchivosCierreComercial(CmtOrdenTrabajoMgl ordenTrabajoMgl) 
            throws DatatypeConfigurationException, FileNotFoundException,
            ApplicationException, SearchCuentasMatricesFault{
        
        CmtOrdenTrabajoMglManager manager = new CmtOrdenTrabajoMglManager();
        return manager.obtenerArchivosCierreComercial(ordenTrabajoMgl);
    }
    
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
    @Override
    public CmtOrdenTrabajoMgl actualizarOtCierreComercial(CmtOrdenTrabajoMgl ot,
            BigDecimal nuevoEstado) throws ApplicationException {
        
        CmtOrdenTrabajoMglManager manager = new CmtOrdenTrabajoMglManager();
        return manager.actualizarOtCierreComercial(ot, nuevoEstado, user, perfil);
    }
    
        
    /**
     * Busca las Ordenes de Trabajo asociadas a un tipo de trabajo.
     *
     * @author Victor Bocanegra
     * @param tipoOtMgl tipo de trabajo
     * @return Ordenes de Trabajo encontrada en el repositorio asociadas a un
     * tipo de trabajo
     * @throws ApplicationException
     */
    @Override
    public List<CmtOrdenTrabajoMgl> findByTipoTrabajo(CmtTipoOtMgl tipoOtMgl) throws ApplicationException {

        CmtOrdenTrabajoMglManager manager = new CmtOrdenTrabajoMglManager();
        return manager.findByTipoTrabajo(tipoOtMgl);
    }
    
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
    @Override
    public void changeStatusCmByFlowAco(CmtOrdenTrabajoMgl ot, String usuario, int perfil)
            throws ApplicationException {
        CmtOrdenTrabajoMglManager manager = new CmtOrdenTrabajoMglManager();
        try {
            manager.changeStatusCmByFlowAco(ot, usuario, perfil);
        } catch (ApplicationException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }
    
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
    @Override
    public CmtOrdenTrabajoMgl findOtByIdAndPermisos(BigDecimal idOt, List<BigDecimal> estadosxflujoList)
            throws ApplicationException {
            
        CmtOrdenTrabajoMglManager manager = new CmtOrdenTrabajoMglManager();
        return manager.findOtByIdAndPermisos(idOt, estadosxflujoList);
    }
      
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
    @Override
    public List<CmtOrdenTrabajoMgl> findByFiltroParaGestionExportarOt(
            List<BigDecimal> estadosxflujoList, CmtFiltroConsultaOrdenesDto filtro) 
            throws ApplicationException {

        CmtOrdenTrabajoMglManager manager = new CmtOrdenTrabajoMglManager();
        return manager.findByFiltroParaGestionExportarOt(estadosxflujoList,filtro);
    }
    
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
    @Override
    public CmtOrdenTrabajoMgl actualizarOtCcmm(CmtOrdenTrabajoMgl ot,
            String usuario, int perfil) throws ApplicationException {

        CmtOrdenTrabajoMglManager manager = new CmtOrdenTrabajoMglManager();
        return manager.actualizarOtCcmm(ot, usuario, perfil);
    }
    
    @Override
    public String crearOtRRporAgendamiento(CmtOrdenTrabajoMgl ot , CmtEstadoxFlujoMgl estadoFlujo,String usuario,int perfil) throws ApplicationException{
        CmtOrdenTrabajoMglManager manager = new CmtOrdenTrabajoMglManager();
        return manager.crearOtRRporAgendamiento(ot, estadoFlujo, usuario, perfil);
    }
    
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
    @Override
    public List<CmtOrdenTrabajoMgl> findByOtCloseForGenerarAcometida(List<BigDecimal> estadosxflujoList,
            CmtFiltroConsultaOrdenesDto filtro, List<CmtTipoOtMgl> tiposOtgeneranAco,
            int paginaSelected, int maxResults) throws ApplicationException {

        CmtOrdenTrabajoMglManager manager = new CmtOrdenTrabajoMglManager();
        return manager.findByOtCloseForGenerarAcometida(estadosxflujoList, filtro, 
                tiposOtgeneranAco, paginaSelected, maxResults);
    }
    
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
    @Override
    public int getCountOtCloseForGenerarAcometida(List<BigDecimal> estadosxflujoList,
            CmtFiltroConsultaOrdenesDto filtro, List<CmtTipoOtMgl> tiposOtgeneranAco) throws ApplicationException {

        CmtOrdenTrabajoMglManager manager = new CmtOrdenTrabajoMglManager();
        return manager.getCountOtCloseForGenerarAcometida(estadosxflujoList, filtro, tiposOtgeneranAco);

    }
    
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
    @Override
    public CmtOrdenTrabajoMgl findIdOtCloseForGenerarAcometidaAndPermisos(List<BigDecimal> estadosxflujoList,
            List<CmtTipoOtMgl> tiposOtgeneranAco, BigDecimal idOt, boolean conPermisos)
            throws ApplicationException {
        
        CmtOrdenTrabajoMglManager manager = new CmtOrdenTrabajoMglManager();
        return manager.findIdOtCloseForGenerarAcometidaAndPermisos
                (estadosxflujoList, tiposOtgeneranAco, idOt,conPermisos);

    }
    
    /**
     * Elimina una orden de trabajo de rr.
     *
     * @author Victor Bocanegra
     * @param agenda con la informacion de la ot
     * @param usuario que realiza la transacion
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @Override
    public boolean eliminarOtRRporAgendamiento(CmtAgendamientoMgl agenda, String usuario)
            throws ApplicationException {

        CmtOrdenTrabajoMglManager manager = new CmtOrdenTrabajoMglManager();
        return manager.eliminarOtRRporAgendamiento(agenda, usuario);
    }
    
    /**
     * Metodo para consultar los trabajos que tiene una CM en RR
     *
     * @author Victor bocanegra
     * @param numeroCuenta cnumero de la cuenta matriz
     * @return ResponseOtEdificiosList encontrado
     * @throws ApplicationException
     */
    @Override
    public ResponseOtEdificiosList ordenTrabajoEdificioQuery(String numeroCuenta)
            throws ApplicationException {

        CmtOrdenTrabajoMglManager manager = new CmtOrdenTrabajoMglManager();
        return manager.ordenTrabajoEdificioQuery(numeroCuenta);
    }
    
    /**
     * Metodo para consumir el nuevo servicio de consulta a una OT hija en onix
     *
     * @author Victor bocanegra
     * @param numeroOtHija numero de la ot hija a consultar
     * @return ResponseOtEdificiosList encontrado
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @Override
    public CmtOnyxResponseDto consultarOTHijaOnyx(String numeroOtHija) throws ApplicationException {
        CmtOrdenTrabajoMglManager manager = new CmtOrdenTrabajoMglManager();
        try {
            return manager.consultarOTHijaWsOnyx(numeroOtHija);
        } catch (DatatypeConfigurationException ex) {
            String msgError = "Error en " + ClassUtils.getCurrentMethodName(this.getClass()) + ": " + ex.getMessage();
            LOGGER.error(msgError, ex);
        }
        return null;
        }
    
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
    @Override
    public String crearOtRRporAgendamientoHhpp(String numeroCuentaParametro,
            TipoOtHhppMgl tipoOtHhppMgl, String usuario, OtHhppMgl ot) throws ApplicationException {

        CmtOrdenTrabajoMglManager manager = new CmtOrdenTrabajoMglManager();
        return manager.crearOtRRporAgendamientoHhpp(numeroCuentaParametro, tipoOtHhppMgl, usuario, ot);
    }
    
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
    @Override
    public boolean ordenTrabajoEdificioDeleteHhpp(String numeroCuentaParametro, String numeroOt, String usuario)
            throws ApplicationException {
        
        CmtOrdenTrabajoMglManager manager = new CmtOrdenTrabajoMglManager();
        return manager.ordenTrabajoEdificioDeleteHhpp(numeroCuentaParametro, numeroOt, usuario);
    }
    
    @Override
    public List<ReporteOtCMDto> findReporteOtCm(HashMap<String, Object> params, String usuario, List<CmtBasicaMgl> listaBasicaDir,
            List<CmtBasicaMgl> listaBasicaCm,
            List<CmtRegionalRr> listacmtRegionalMgl,
            List<CmtComunidadRr> listacmtComunidadRr,
            List<CmtEstadoIntxExtMgl> listaEstadosIntExt) throws ApplicationException {
        List<ReporteOtCMDto> listOrdenesCcmmDir = null;
        CmtOrdenTrabajoReportThreadManager manager = new CmtOrdenTrabajoReportThreadManager();
        listOrdenesCcmmDir = manager.getReporteOtCM(params, usuario, listaBasicaDir, listaBasicaCm, listacmtRegionalMgl, listacmtComunidadRr, listaEstadosIntExt);
        return listOrdenesCcmmDir;
    }

    @Override
    public int findReporteOtCmTotal(HashMap<String, Object> params, String usuario) throws ApplicationException {
        int totalRegistros;
        CmtOrdenTrabajoReportThreadManager manager = new CmtOrdenTrabajoReportThreadManager();
        totalRegistros = manager.getReporteEstadoActualOtTotal(params, usuario);
        return totalRegistros;
    }
    
    @Override
    public void findReporteHistoricoOtCm(HashMap<String, Object> params, String usuario) throws ApplicationException {
        CmtOrdenTrabajoReportThreadManager manager = new CmtOrdenTrabajoReportThreadManager();
        manager.getReporteHistoricoOtCM(params, usuario);
    }

    @Override
    public int findReporteHistoricoOtCmTotal(HashMap<String, Object> params, String usuario) throws ApplicationException {
        int totalRegistros;
        CmtOrdenTrabajoReportThreadManager manager = new CmtOrdenTrabajoReportThreadManager();
        totalRegistros = manager.getReporteHistoricoOtTotal(params, usuario);
        return totalRegistros;
    }
    
    
      @Override
    public void findReporteOtDIR(HashMap<String, Object> params, String usuario) throws ApplicationException {
        CmtOrdenTrabajoReportThreadManager manager = new CmtOrdenTrabajoReportThreadManager();
        manager.getReporteOtDIR(params, usuario);
    }

    @Override
    public int findReporteOtDIRTotal(HashMap<String, Object> params, String usuario) throws ApplicationException {
        CmtOrdenTrabajoReportThreadManager manager = new CmtOrdenTrabajoReportThreadManager();
        return manager.getReporteOtDIRTotal(params, usuario);
    }

    @Override
    public List<ReporteHistoricoOtDIRDto> findReporteHistoricoOtDIR(HashMap<String, Object> params, String usuario, List<CmtBasicaMgl> listaBasicaDir,
            List<CmtBasicaMgl> listaBasicaCm,
            List<CmtRegionalRr> listacmtRegionalMgl,
            List<CmtComunidadRr> listacmtComunidadRr,
            List<CmtEstadoIntxExtMgl> listaEstadosIntExt) throws ApplicationException {
        List<ReporteHistoricoOtDIRDto> listaHist = null;
        CmtOrdenTrabajoReportThreadManager manager = new CmtOrdenTrabajoReportThreadManager();
        listaHist = manager.getReporteHistoricoOtDIR(params, usuario, listaBasicaDir,
                listaBasicaCm,
                listacmtRegionalMgl,
                listacmtComunidadRr,
                listaEstadosIntExt);
        return listaHist;
    }

    @Override
    public int findReporteHistoricoOtDIRTotal(HashMap<String, Object> params, String usuario) throws ApplicationException {
        CmtOrdenTrabajoReportThreadManager manager = new CmtOrdenTrabajoReportThreadManager();
        return manager.getReporteHistoricoOtDIRTotal(params, usuario);
    }
    
    /**
     * Busqueda el ultimo estado de la orden en auditoria con estado razonado
     *
     * @author victor bocanegra
     * @param ot valor de la cuenta matriz para la busqueda
     * @param estadoIntActual valor de la basica de tecnologia para la busqueda
     * @return CmtOrdenTrabajoAuditoriaMgl
     */
    @Override
    public CmtOrdenTrabajoAuditoriaMgl findUltimoEstadoOtRazonada(CmtOrdenTrabajoMgl ot,
            BigDecimal estadoIntActual) throws ApplicationException {
        CmtOrdenTrabajoMglManager manager = new CmtOrdenTrabajoMglManager();
        return manager.findUltimoEstadoOtRazonada(ot, estadoIntActual);
    }
    
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
    @Override
    public boolean cerrarOTRR(CmtOrdenTrabajoMgl ot, String numeroOT,
            CmtEstadoxFlujoMgl estadoFlujo, String usuario, int perfil) throws ApplicationException {
        CmtOrdenTrabajoMglManager manager = new CmtOrdenTrabajoMglManager();
        return manager.cerrarOTRR(ot, numeroOT, estadoFlujo, usuario, perfil);
    }
    
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
     * @param ot orden de direcciones
     * @param notaCierre nota de cierre de OT en RR.
     * @return true or false
     */
    @Override
    public boolean actualizarEstadoResultadoOTRRHhpp(String numeroCuentaParametro, String numeroOt,
            String usuario, TipoOtHhppMgl tipoOtHhppMgl, 
            boolean isIncial, OtHhppMgl ot, String notaCierre)
            throws ApplicationException {
        CmtOrdenTrabajoMglManager manager = new CmtOrdenTrabajoMglManager();
        return manager.actualizarEstadoResultadoOTRRHhpp(numeroCuentaParametro, numeroOt,
                usuario, tipoOtHhppMgl, isIncial, ot, notaCierre);
    }
    
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
    @Override
    public CmtOrdenTrabajoMgl bloquearDesbloquearOrden(CmtOrdenTrabajoMgl orden,
            boolean bloqueo) throws ApplicationException {
        CmtOrdenTrabajoMglManager manager = new CmtOrdenTrabajoMglManager();
        return manager.bloquearDesbloquearOrden(orden, bloqueo, user, perfil);
    }
    
     /**
     * Metodo para notificar una orden de trabajo en ONIX
     *
     * @author Victor Bocanegra
     * @param orden orden a notificar
     * @param numOtHija numero de la Ot hija
     * @return MERNotifyResponseType
     * @throws ApplicationException
     */
    @Override
    public MERNotifyResponseType notificarOrdenOnix(CmtOrdenTrabajoMgl orden, String numOtHija)
            throws ApplicationException {
        CmtOrdenTrabajoMglManager manager = new CmtOrdenTrabajoMglManager();
        return manager.notificarOrdenOnix(orden,numOtHija);
    }

    @Override
    public void eliminarOTByCM(BigDecimal idCuentaMatriz) throws ApplicationException {
        CmtOrdenTrabajoMglManager manager = new CmtOrdenTrabajoMglManager();
        manager.eliminarOTByCM(idCuentaMatriz);
    }
    
    @Override
    public int countByFiltroParaGestionExportarOt(
            List<BigDecimal> estadosxflujoList, BigDecimal gpoDepartamento,
            BigDecimal gpoCiudad, BigDecimal idTipoOt, BigDecimal estadoInternoBasicaId,
            BigDecimal tecnologia, String codigoReg) throws ApplicationException {

        CmtOrdenTrabajoMglManager manager = new CmtOrdenTrabajoMglManager();
        return manager.countByFiltroParaGestionExportarOt(estadosxflujoList, gpoDepartamento,
                gpoCiudad, idTipoOt, estadoInternoBasicaId, tecnologia, codigoReg);
    }
    
       @Override
    public List<CmtOrdenTrabajoMgl> findOtAcometidaSubEdificio( CmtSubEdificioMgl cmtSubEdificioMgl) 
            throws ApplicationException {
        CmtOrdenTrabajoMglManager manager = new CmtOrdenTrabajoMglManager();
        return manager.findOtAcometidaSubEdificio( cmtSubEdificioMgl);
    }
    

    
}
