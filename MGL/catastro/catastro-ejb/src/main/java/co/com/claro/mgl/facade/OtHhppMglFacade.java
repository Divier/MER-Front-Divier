/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.OtHhppMglManager;
import co.com.claro.mgl.dtos.FiltroConsultaOtDireccionesDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.AuditoriaHhppDto;
import co.com.claro.mgl.jpa.entities.DireccionMgl;
import co.com.claro.mgl.jpa.entities.OtHhppMgl;
import co.com.claro.mgl.jpa.entities.Solicitud;
import co.com.claro.mgl.jpa.entities.SubDireccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.ws.cm.request.RequestCreaDireccionOtDirecciones;
import com.amx.service.exp.operation.mernotify.v1.MERNotifyResponseType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Juan David Hernandez
 */
@Stateless
public class OtHhppMglFacade implements OtHhppMglFacadeLocal {

    private static final Logger LOGGER = LogManager.getLogger(OtHhppMglFacade.class);
    private String user = "";
    private int perfil = 0;

    @Override
    public OtHhppMgl update(OtHhppMgl t)
            throws ApplicationException {
        OtHhppMglManager otHhppMglManager
                = new OtHhppMglManager();
        return otHhppMglManager.update(t);
    }
  

    public boolean eliminarTipoOtHhpp(OtHhppMgl t) throws ApplicationException {
        OtHhppMglManager otHhppMglManager
                = new OtHhppMglManager();
        return otHhppMglManager.eliminarOtHhpp(t, user, perfil);
    }

    @Override
    public boolean delete(OtHhppMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public OtHhppMgl create(OtHhppMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public OtHhppMgl findById(OtHhppMgl sqlData)
            throws ApplicationException {
        OtHhppMglManager otHhppMglManager
                = new OtHhppMglManager();
        return otHhppMglManager.findById(sqlData);
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
     * Listado de ot por id
     *
     * @author Juan David Hernandez
     * @param id
     * @return
     * @throws ApplicationException
     */
    @Override
    public List<OtHhppMgl> findOtHhppById(BigDecimal id) throws ApplicationException {
        OtHhppMglManager otHhppMglManager = new OtHhppMglManager();
        return otHhppMglManager.findOtHhppById(id);
    }

    /**
     * Listado de todos los ot de la base de datos
     *
     * @author Juan David Hernandez
     *
     * @return listado de ot
     * @throws ApplicationException
     */
    @Override
    public List<OtHhppMgl> findAll() throws ApplicationException {
        OtHhppMglManager otHhppMglManager = new OtHhppMglManager();
        return otHhppMglManager.findAll();
    }

    /**
     * Listado de todos los ot de la base de datos
     *
     * @author Juan David Hernandez
     *
     * @return listado de tipo de ot
     * @throws ApplicationException
     */
    @Override
    public int countAllOtHhpp(List<String> listadoRolesUsuario) throws ApplicationException {
        OtHhppMglManager otHhppMglManager = new OtHhppMglManager();
        return otHhppMglManager.countAllOtHhpp(listadoRolesUsuario);
    }

    /**
     * Listado de todos los ot de la base de datos
     *
     * @author Juan David Hernandez
     * @param filtroConsultaOtDireccionesDto
     *
     * @return listado de ot
     * @throws ApplicationException
     */
    @Override
    public List<OtHhppMgl> findAllOtHhppPaginada(int firstResult,
            int maxResults, List<String> listadoRolesUsuario, FiltroConsultaOtDireccionesDto filtroConsultaOtDireccionesDto) throws ApplicationException {
        OtHhppMglManager otHhppMglManager = new OtHhppMglManager();
        return otHhppMglManager.findAllOtHhppPaginada(firstResult,
                maxResults, listadoRolesUsuario,filtroConsultaOtDireccionesDto);
    }

    /**
     * Crea un ot en la base de datos
     *
     * @author Juan David Hernandez
     * @param t
     * @param tecnologiaList
     * @return
     * @throws ApplicationException
     */
    @Override
    public OtHhppMgl crearOtHhpp(OtHhppMgl t, List<CmtBasicaMgl> tecnologiaList)
            throws ApplicationException {
        OtHhppMglManager otHhppMglManager = new OtHhppMglManager();
        return otHhppMglManager.createOtHhpp(t, user, perfil, tecnologiaList);
    }

    /**
     * Crea una ot a partir de una solicitud.Permite crear una OT en la base de
        datos y su respectiva relación con las tecnologias a partir de una
        solicitud
     *
     * @author ortizjaf
     * @param tecnologiaList lista de tecnologias seleccionadas
     * @param perfil
     * @return
     * @throws ApplicationException
     */
    @Override
    public OtHhppMgl createOtFromSolicitud(Solicitud solicitudGestion, String usuario,
            int perfil, List<CmtBasicaMgl> tecnologiaList) throws ApplicationException {
        OtHhppMglManager otHhppMglManager = new OtHhppMglManager();
        return otHhppMglManager.createOtFromSolicitud(
                solicitudGestion, usuario, perfil, tecnologiaList);
    }

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
    @Override
    public boolean validarDatosOtToCreate(OtHhppMgl ot, List<CmtBasicaMgl> tecnologiaList)
            throws ApplicationException {
        OtHhppMglManager otHhppMglManager = new OtHhppMglManager();
        return otHhppMglManager.validarDatosOtToCreate(ot, tecnologiaList);
    }

    @Override
    public OtHhppMgl editarOtHhpp(OtHhppMgl t) throws ApplicationException {
        OtHhppMglManager otHhppMglManager = new OtHhppMglManager();
        return otHhppMglManager.editarOtHhpp(t, user, perfil);
    }

    /**
     * Listado de todos los ot de la base de datos por id direccion detallada
     *
     * @author Juan David Hernandez
     * @param subDireccionId
     * @param direccionId
     *
     * @return listado de ot
     * @throws ApplicationException
     */
    @Override
    public List<OtHhppMgl> findAllOtHhppByDireccionDetalladaId(int firstResult,
            int maxResults, BigDecimal direccionId, BigDecimal subDireccionId ,OtHhppMgl filtro) throws ApplicationException {
        OtHhppMglManager otHhppMglManager = new OtHhppMglManager();
        return otHhppMglManager
                .findAllOtHhppByDireccionDetalladaId(firstResult,
                        maxResults, direccionId, subDireccionId , filtro);
    }

    /**
     * Listado de todos los ot de la base de datos
     *
     * @author Juan David Hernandez
     * @param direccionId
     * @param subDireccionId
     *
     * @return listado de tipo de ot
     * @throws ApplicationException
     */
    @Override
    public int countAllOtHhppByDireccionDetalladaId(BigDecimal direccionId, BigDecimal subDireccionId, OtHhppMgl filtro) throws ApplicationException {
        OtHhppMglManager otHhppMglManager = new OtHhppMglManager();
        return otHhppMglManager.countAllOtHhppByDireccionDetalladaId(direccionId, subDireccionId,filtro);
    }

    /**
     * Se obtiene el color de la alerta correspondiente a los ans del tipo de ot
     * relacionado.
     *
     * @author Juan David Hernandez
     * @return color de la alerta de la ot
     */
    @Override
    public String obtenerColorAlerta(OtHhppMgl otHhppMgl) throws ApplicationException {
        OtHhppMglManager otHhppMglManager = new OtHhppMglManager();
        return otHhppMglManager.obtenerColorAlerta(otHhppMgl);
    }

    /**
     * Se valida la cobertura del nodo para las tecnologias seleccionadas
     *
     * @author Orlando Velasquez Diaz
     * @param otHhppMgl
     * @param tecnologiaList
     * @return color de la alerta de la ot
     * @throws ApplicationException
     */
    @Override
    public boolean validarCoberturaDeNodo(OtHhppMgl otHhppMgl,
            List<CmtBasicaMgl> tecnologiaList) throws ApplicationException {
        OtHhppMglManager otHhppMglManager = new OtHhppMglManager();
        return otHhppMglManager.validarCoberturaDeNodo(otHhppMgl, tecnologiaList);
    }

    /**
     * Se crea la Ot independientemente
     *
     * @author Orlando Velasquez Diaz
     * @param otToCreate
     * @param tecnologiaList
     * @return color de la alerta de la ot
     * @throws ApplicationException
     */
    @Override
    public OtHhppMgl createIndependentOt(OtHhppMgl otToCreate,
            List<CmtBasicaMgl> tecnologiaList) throws ApplicationException {
        OtHhppMglManager otHhppMglManager = new OtHhppMglManager();
        return otHhppMglManager.createIndependentOt(otToCreate, user, perfil, tecnologiaList);

    }

    /**
     * Se crea direccion para una Ot
     *
     * @author Orlando Velasquez Diaz
     * @param idCentroPoblado identificacion del centro poblado
     * @return color de la alerta de la ot
     * @throws ApplicationException
     */
    @Override
    public boolean createDireccionParaOtDirecciones(RequestCreaDireccionOtDirecciones request,
            BigDecimal idCentroPoblado) throws ApplicationException {

        OtHhppMglManager otHhppMglManager = new OtHhppMglManager();
        return otHhppMglManager.createDireccionParaOtDirecciones(request, idCentroPoblado, user, perfil);
    }

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
    @Override
    public OtHhppMgl findOtByDireccionAndSubDireccion(DireccionMgl direccion,
            SubDireccionMgl subDireccion, CmtBasicaMgl estado) throws ApplicationException {
        OtHhppMglManager otHhppMglManager = new OtHhppMglManager();
        return otHhppMglManager.findOtByDireccionAndSubDireccion(direccion, subDireccion, estado);
    }

    @Override
    public int countByFiltro(
            FiltroConsultaOtDireccionesDto filtroConsultaOtDireccionesDto) throws ApplicationException {
        OtHhppMglManager otHhppMglManager = new OtHhppMglManager();
        return otHhppMglManager.countByFiltro(filtroConsultaOtDireccionesDto);

    }
    
    /**
     * Bocanegra vm metodo para buscar una orden de trabajo con el id de la
     * orden
     *
     * @param idOt
     * @return OtHhppMgl
     */
    @Override
    public OtHhppMgl findOtByIdOt(BigDecimal idOt)
            throws ApplicationException {
        OtHhppMglManager otHhppMglManager = new OtHhppMglManager();
        return otHhppMglManager.findOtByIdOt(idOt);
    }
    
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
    @Override
    public OtHhppMgl bloquearDesbloquearOrden(OtHhppMgl orden,
            boolean bloqueo) throws ApplicationException {
        OtHhppMglManager otHhppMglManager = new OtHhppMglManager();
        return otHhppMglManager.bloquearDesbloquearOrden(orden, bloqueo, user, perfil);
    }
   
    /**
     * Metodo para notificar una orden de trabajo en ONYX
     *
     * @author Victor Bocanegra
     * @param orden orden a notificar
     * @param numOtHija numero de la OT hija
     * @return MERNotifyResponseType
     * @throws ApplicationException
     */
    @Override
    public MERNotifyResponseType notificarOrdenOnix(OtHhppMgl orden, String numOtHija)
            throws ApplicationException {

        OtHhppMglManager otHhppMglManager = new OtHhppMglManager();
        return otHhppMglManager.notificarOrdenOnix(orden, numOtHija);
    }
    
        /**
     * Bocanegra vm metodo para buscar una orden de trabajo de HHPP por
     * direccion y subdireccion
     *
     * @param direccion
     * @param subDireccion
     * @return OtHhppMgl
     */
    @Override
    public OtHhppMgl findOtHhppByDireccionAndSubDireccion(DireccionMgl direccion,
            SubDireccionMgl subDireccion)
            throws ApplicationException {
        OtHhppMglManager otHhppMglManager = new OtHhppMglManager();
        return otHhppMglManager.findOtHhppByDireccionAndSubDireccion(direccion, subDireccion);
    }
     /**
     * Metodo que contruye la tabla de auditorias de una orden de trabajo
     * @param otHhppMgl {@link OtHhppMgl}
     * @author Luz Villalobos
     * @return List<AuditoriaHhppDto>
     */
    @Override
    public List<AuditoriaHhppDto> construirAuditoria(OtHhppMgl otHhppMgl)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (otHhppMgl != null && otHhppMgl.getOtHhppId() != null) {
            OtHhppMglManager otHhppMglManager = new OtHhppMglManager();
            return otHhppMglManager.construirAuditoria(otHhppMgl);
        } else {
            return null;
        }
    }
    
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
    @Override
    public List<OtHhppMgl> findOtHhppByDireccionAndTecnologias(DireccionMgl direccion,
            SubDireccionMgl subDireccion, CmtBasicaMgl tecnologia)
            throws ApplicationException {
        OtHhppMglManager otHhppMglManager = new OtHhppMglManager();
        return otHhppMglManager.findOtHhppByDireccionAndTecnologias(direccion, subDireccion, tecnologia);
    }
}
