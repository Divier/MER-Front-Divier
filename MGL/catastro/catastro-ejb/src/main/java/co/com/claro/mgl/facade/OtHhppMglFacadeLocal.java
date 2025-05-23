/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.dtos.FiltroConsultaOtDireccionesDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.AuditoriaHhppDto;
import co.com.claro.mgl.jpa.entities.DireccionMgl;
import co.com.claro.mgl.jpa.entities.OtHhppMgl;
import co.com.claro.mgl.jpa.entities.Solicitud;
import co.com.claro.mgl.jpa.entities.SubDireccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.ws.cm.request.RequestCreaDireccionOtDirecciones;
import com.amx.service.exp.operation.mernotify.v1.MERNotifyResponseType;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Juan David Hernandez
 */
public interface OtHhppMglFacadeLocal extends BaseFacadeLocal<OtHhppMgl> {

    /**
     * Listado de ot por id
     *
     * @author Juan David Hernandez
     * @param otHhpp
     * @return
     * @throws ApplicationException
     */
     List<OtHhppMgl> findOtHhppById(BigDecimal otHhpp)
            throws ApplicationException;

    /**
     * Listado de todos los tipo de ot de la base de datos
     *
     * @author Juan David Hernandez
     * @return
     * @throws ApplicationException
     */
     List<OtHhppMgl> findAll() throws ApplicationException;

    /**
     * Crea una ot en la base de datos
     *
     * @author Juan David Hernandez
     * @param t
     * @param tecnologiaList
     * @return
     * @throws ApplicationException
     */
     OtHhppMgl crearOtHhpp(OtHhppMgl t, List<CmtBasicaMgl> tecnologiaList)
            throws ApplicationException;

    /**
     * Crea una ot a partir de una solicitud.Permite crear una OT en la base de
        datos y su respectiva relación con las tecnologias a partir de una
        solicitud
     *
     * @author ortizjaf
     * @param solicitudGestion
     * @param usuario
     * @param perfil
     * @param tecnologiaList lista de tecnologias seleccionadas
     * @return
     * @throws ApplicationException
     */
    OtHhppMgl createOtFromSolicitud(Solicitud solicitudGestion, String usuario,
            int perfil, List<CmtBasicaMgl> tecnologiaList) throws ApplicationException;

    /**
     * Valida la data de la OT. Función para validar si los datos de la OT a
     * crear estan completos
     *
     * @author ortizjaf
     * @param ot Orden de trabajo a validar
     * @param tecnologiaList lista de Tecnologias
     * @return true si los datos permiten la creacion de la OT
     * @throws ApplicationException
     */
    boolean validarDatosOtToCreate(OtHhppMgl ot, List<CmtBasicaMgl> tecnologiaList)
            throws ApplicationException;

    void setUser(String mUser, int mPerfil) throws ApplicationException;

    /**
     * Cuenta de todos las ot de la base de datos
     *
     * @author Juan David Hernandez
     * @param listadoRolesUsuario lsitado de roles del usuario logueado
     * @return
     * @throws ApplicationException
     */
     int countAllOtHhpp(List<String> listadoRolesUsuario) throws ApplicationException;

    /**
     * Listado de todos los ot de la base de datos
     *
     * @author Juan David Hernandez
     * @param firstResult
     * @param maxResults
     * @param listadoRolesUsuario lsitado de roles del usuario logueado
     * @param filtroConsultaOtDireccionesDto
     * @return
     * @throws ApplicationException
     */
     List<OtHhppMgl> findAllOtHhppPaginada(int firstResult,
            int maxResults, List<String> listadoRolesUsuario, FiltroConsultaOtDireccionesDto filtroConsultaOtDireccionesDto) throws ApplicationException;

    /**
     * Edita una ot en la base de datos
     *
     * @author Juan David Hernandez
     * @param t
     * @return
     * @throws ApplicationException
     */
     OtHhppMgl editarOtHhpp(OtHhppMgl t)
            throws ApplicationException;

    /**
     * Listado de todos los ot de la base de datos por id direccion detallada
     *
     * @author Juan David Hernandez
     * @param firstResult
     * @param subDireccionId
     * @param direccionId
     * @return
     * @throws ApplicationException
     */
     List<OtHhppMgl> findAllOtHhppByDireccionDetalladaId(int firstResult,
            int maxResults, BigDecimal direccionId, BigDecimal subDireccionId ,OtHhppMgl filtro) throws ApplicationException;

    /**
     * Cuenta de todos las ot de la base de datos
     *
     * @author Juan David Hernandez
     * @param direccionId
     * @param subDireccionId
     * @return
     * @throws ApplicationException
     */
     int countAllOtHhppByDireccionDetalladaId(BigDecimal direccionId, BigDecimal subDireccionId, OtHhppMgl filtro)
            throws ApplicationException;

    /**
     * Se obtiene el color de la alerta correspondiente a los ans del tipo de ot
     * relacionado.
     *
     * @author Juan David Hernandez
     * @param otHhppMgl
     * @return color de la alerta de la ot
     * @throws ApplicationException
     */
     String obtenerColorAlerta(OtHhppMgl otHhppMgl) throws ApplicationException;

    /**
     * Se valida la cobertura del nodo para las tecnologias seleccionadas
     *
     * @author Orlando Velasquez Diaz
     * @param otHhppMgl
     * @param tecnologiaList
     * @return color de la alerta de la ot
     * @throws ApplicationException
     */
     boolean validarCoberturaDeNodo(OtHhppMgl otHhppMgl,
            List<CmtBasicaMgl> tecnologiaList) throws ApplicationException;

    /**
     * Se crea la Ot independientemente
     *
     * @author Orlando Velasquez Diaz
     * @param otToCreate
     * @param tecnologiaList
     * @return color de la alerta de la ot
     * @throws ApplicationException
     */
    OtHhppMgl createIndependentOt(OtHhppMgl otToCreate,
            List<CmtBasicaMgl> tecnologiaList) throws ApplicationException;

    /**
     * Se crea direccion para una Ot
     *
     * @author Orlando Velasquez Diaz
     * @param request
     * @param idCentroPoblado identificacion del centro poblado
     * @return color de la alerta de la ot
     * @throws ApplicationException
     */
     boolean createDireccionParaOtDirecciones(RequestCreaDireccionOtDirecciones request,
            BigDecimal idCentroPoblado) throws ApplicationException;

    /**
     * Busca una Ot por direccion y subdireccion
     *
     * @author Orlando Velasquez Diaz
     * @param direccion
     * @param subDireccion
     * @param estado
     * @return color de la alerta de la ot
     * @throws ApplicationException
     */
    OtHhppMgl findOtByDireccionAndSubDireccion(DireccionMgl direccion,
            SubDireccionMgl subDireccion, CmtBasicaMgl estado) throws ApplicationException;

    /**
     * Busca las Ot acordes al filtro enviado
     *
     * @author Orlando Velasquez Diaz
     * @param filtroConsultaOtDireccionesDto Dto con los filtros
     * @return color de la alerta de la ot
     * @throws ApplicationException
     */
     int countByFiltro(FiltroConsultaOtDireccionesDto filtroConsultaOtDireccionesDto) throws ApplicationException;
    
    /**
     * Bocanegra vm metodo para buscar una orden de trabajo con el id de la
     * orden
     *
     * @param idOt
     * @return OtHhppMgl
     */
    OtHhppMgl findOtByIdOt(BigDecimal idOt)
            throws ApplicationException;
    
    
        /**
     * Bloquea o Desbloquea una orden. Permite realizar el bloqueo o desbloqueo
     * de una orden en el repositorio para la gestion.
     *
     * @author Victor Bocanegra
     * @param orden orden a bloquear o desbloquear
     * @param bloqueo si es bloqueo o no
     * @return OtHhppMgl
     * @throws ApplicationException
     */
    OtHhppMgl bloquearDesbloquearOrden(OtHhppMgl orden,
            boolean bloqueo) throws ApplicationException;
  
    /**
     * Metodo para notificar una orden de trabajo en ONYX
     *
     * @author Victor Bocanegra
     * @param orden orden a notificar
     * @param numOtHija numero de la OT hija
     * @return MERNotifyResponseType
     * @throws ApplicationException
     */
    MERNotifyResponseType notificarOrdenOnix(OtHhppMgl orden, String numOtHija)
            throws ApplicationException;
    
    /**
     * Bocanegra vm metodo para buscar una orden de trabajo de HHPP por
     * direccion y subdireccion
     *
     * @param direccion
     * @param subDireccion
     * @return OtHhppMgl
     */
    OtHhppMgl findOtHhppByDireccionAndSubDireccion(DireccionMgl direccion,
            SubDireccionMgl subDireccion)
            throws ApplicationException;
    
        /**
     * Busqueda de Ordenes de trabajo por direccion y subdireccion y tecnologia
     *
     * @author Victor Bocanegra
     * @param direccion
     * @param subDireccion
     * @param tecnologia tecnologia para la busqueda
     * @return List<OtHhppMgl>
     * @throws ApplicationException
     */
     List<OtHhppMgl> findOtHhppByDireccionAndTecnologias(DireccionMgl direccion,
            SubDireccionMgl subDireccion, CmtBasicaMgl tecnologia)
            throws ApplicationException;
     
    /**
     * Metodo que contruye la tabla de auditorias de una orden de trabajo
     * @param otHhppMgl {@link OtHhppMgl}
     * @author Luz Villalobos
     * @return List<AuditoriaHhppDto>
     */ 
    public List<AuditoriaHhppDto> construirAuditoria(OtHhppMgl otHhppMgl) 
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;
}
