/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtDireccionDetalleMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtRunableFraudesMasivoHHPPDesmarcadoDtoManager;
import co.com.claro.mgl.businessmanager.cm.CmtRunableFraudesMasivoHHPPDtoManager;
import co.com.claro.mgl.dtos.FraudesHHPPDesmarcadoMasivoDto;
import co.com.claro.mgl.dtos.FraudesHHPPMasivoDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.DireccionMgl;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.SubDireccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import co.com.claro.mgl.rest.dtos.*;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.telmex.catastro.data.AddressService;
import com.jlcg.db.exept.ExceptionDB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.Stateless;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author valbuenayf
 */
@Stateless
public class CmtDireccionDetalleMglFacade implements CmtDireccionDetalleMglFacadeLocal {
    
    private static final Logger LOGGER = LogManager.getLogger(CmtDireccionDetalleMglFacade.class);
    
    private final CmtDireccionDetalleMglManager cmtDireccionDetalle;

    public CmtDireccionDetalleMglFacade() {
        cmtDireccionDetalle = new CmtDireccionDetalleMglManager();
    }

    @Override
    public CmtAddressResponseDto consultarDireccion(CmtDireccionDetalladaRequestDto cmtDireccionDetalladaRequestDto) {
        CmtDireccionDetalleMglManager cmtDireccionDetalle1 = new CmtDireccionDetalleMglManager();
        return cmtDireccionDetalle1.ConsultaDireccion(cmtDireccionDetalladaRequestDto);
    }

    @Override
   public CmtAddressResponseDto getNodeIDsByAccount(getNodeIDsByAccountRequestDto nodeIDsByAccountRequestDto) {
       CmtDireccionDetalleMglManager cmtDireccionDetalle1 = new CmtDireccionDetalleMglManager();
       return cmtDireccionDetalle1.getNodeIDsByAccount(nodeIDsByAccountRequestDto);
   }
    
    @Override
    public CmtAddressGeneralResponseDto ConsultaDireccionGeneral(CmtDireccionRequestDto cmtDireccionRequestDto) {
        CmtDireccionDetalleMglManager cmtDireccionDetalle1 = new CmtDireccionDetalleMglManager();
        try {
            return cmtDireccionDetalle1.ConsultaDireccionGeneral(cmtDireccionRequestDto);
        } catch (ExceptionDB | CloneNotSupportedException ex) {
            String msgError = "Error en " + ClassUtils.getCurrentMethodName(this.getClass()) + ": " + ex.getMessage();
            LOGGER.error(msgError, ex);
        } catch (ApplicationException ex) {
            String msgError = "Error al consultar dirección general" + ex.getMessage();
            LOGGER.error(msgError, ex);
        }
        return null;
    }

    @Override
    public List<HhppMgl> findHhppByDireccion(DrDireccion direccion,
            BigDecimal ciudadGpo, boolean evaluarEstadoRegistro, int paginaSeleccionada,
            int maxResults, boolean consumoDireccionGeneralWs) throws ApplicationException {
        return cmtDireccionDetalle.findHhppByDireccion(direccion, ciudadGpo, evaluarEstadoRegistro,
                paginaSeleccionada, maxResults, consumoDireccionGeneralWs);
    }

    @Override
    public List<CmtDireccionDetalladaMgl> findDireccionByDireccionDetallada(DrDireccion direccion,
            BigDecimal ciudadGpo, boolean evaluarEstadoRegistro, int paginaSeleccionada,
            int maxResults, boolean consumoDireccionGeneralWs) throws ApplicationException {
        return cmtDireccionDetalle.findDireccionByDireccionDetallada(direccion, ciudadGpo, evaluarEstadoRegistro,
                paginaSeleccionada, maxResults, consumoDireccionGeneralWs);
    }


    @Override
    public int countHhppByDireccion(DrDireccion direccion,
            BigDecimal ciudadGpo) throws ApplicationException {
        return cmtDireccionDetalle.countHhppByDireccion(direccion, ciudadGpo);
    }

    /**
     * Obtiene una dirección detallada por id de dirección y id de la
     * subdireccion
     *
     * @param dirId id de la dirección
     * @param sdirId id de la subdireccion
     *
     * @return dirección detalla encontrada
     *
     * @author Juan David Hernandez
     * @throws ApplicationException
     */
    @Override
    public List<CmtDireccionDetalladaMgl> findDireccionDetallaByDirIdSdirId(BigDecimal dirId,
            BigDecimal sdirId) throws ApplicationException {
        return cmtDireccionDetalle.findDireccionDetallaByDirIdSdirId(dirId, sdirId);
    }


    @Override
    public CmtDireccionDetalladaMgl buscarDireccionIdDireccion(BigDecimal direccionDetalladaId) throws ApplicationException {
        return cmtDireccionDetalle.buscarDireccionIdDireccion(direccionDetalladaId);
    }


    @Override
    public int countDireccionByDireccionDetallada(DrDireccion direccion,
            BigDecimal ciudadGpo) throws ApplicationException {
        return cmtDireccionDetalle.countDireccionByDireccionDetallada(direccion, ciudadGpo);

    }

    @Override
    public DrDireccion parseCmtDireccionDetalladaMglToDrDireccion(CmtDireccionDetalladaMgl direccionDetMgl) {
        return cmtDireccionDetalle.parseCmtDireccionDetalladaMglToDrDireccion(direccionDetMgl);
    }
    
    
    /**
     * Realiza la consulta del listado de <b>HHPP</b> por <i>Coordenadas</i>, de
     * acuerdo a un rango de <i>Desviaci&oacute;n</i>.
     *
     * @param coordenadasDto DTO que contiene la informaci&oacute;n de las
     * Coordenadas y su Desviaci&oacute;n.
     * @return Listado de entidad Direcci&oacute;n Detallada.
     * @throws ApplicationException
     */
    @Override
    public List<CmtDireccionDetalladaMgl> findDireccionDetalladaByCoordenadas(CmtRequestHhppByCoordinatesDto coordenadasDto)
            throws ApplicationException {
        try {
            return ( cmtDireccionDetalle.findDireccionDetalladaByCoordenadas(coordenadasDto) );
        } catch (ApplicationException ex) {
            LOGGER.error(ex.getMessage());
            return null;
        }
    }
    
    
    /**
     * Obtiene listado de HHPP por coordenada a un rango de desviaci&oacute;n.
     *
     * @author Juan David Hernandez
     * @param coordenadasDto DTO que contiene la informaci&oacute;n de las 
     * Coordenadas y su Desviaci&oacute;n.
     * @return DTO de Respuesta de Direcci&oacute;n General.
     * @throws ApplicationException
     */
    @Override
    public CmtAddressGeneralResponseDto findDireccionByCoordenadas(CmtRequestHhppByCoordinatesDto coordenadasDto)
            throws ApplicationException {
        try {
            return cmtDireccionDetalle.findDireccionByCoordenadas(coordenadasDto);
        } catch (ApplicationException ex) {
            LOGGER.error(ex.getMessage());
            return null;
        }
    }
    
    
     /**
     * Busqueda de direccion detallada por Drdireccion
     *
     * @param drDir id de drDireccion
     * @param centropoblado
     *
     * @return dirección detalla encontrada
     *
     * @author Lenis Cardenas
     * @throws ApplicationException
     */
    @Override
    public List<CmtDireccionDetalladaMgl> findDireccionDetalladaByDrDireccion(DrDireccion drDir, BigDecimal centropoblado) throws ApplicationException {
        return cmtDireccionDetalle.buscarDireccionDetalladaTabulada(drDir, centropoblado);
    }
    
     /**
     * Buscar DrDireccion seg&uacute;n el par&aacute;metro. Se busca la
     * DrDireccion del Hhpp.
     *
     * @author becerraarmr
     * @param hhppMgl hhpp para realizar la busqueda.
     * @return null si no enonctr&oacute; una DrDireccion v&aacute;lida
     * @throws ApplicationException is hay alg&uacute;n
     * inconveniente al buscar en la base de datos o si la respuesta es nula.
     */
    @Override
    public CmtDireccionDetalladaMgl findByHhPP(HhppMgl hhppMgl) throws ApplicationException {
        return cmtDireccionDetalle.findByHhPP(hhppMgl);
    }
 
    @Override
    public CmtDireccionDetalladaMgl findById(BigDecimal idDireccionDetallada)
            throws ApplicationException {
        return cmtDireccionDetalle.findById(idDireccionDetallada);
    }
    
    @Override
    public List<CmtDireccionDetalladaMgl> findDireccionDetalladaByDireccionTexto(String direccionTexto,
            BigDecimal ciudadGpo, boolean resultadoUnico, String barrio, String tipoDireccion) throws ApplicationException {
        return cmtDireccionDetalle.findDireccionDetalladaByDireccionTexto(direccionTexto,ciudadGpo,resultadoUnico, barrio, tipoDireccion);
    }
    
    @Override
    public List<CmtDireccionDetalladaMgl> buscarDireccionDetalladaNivelesComplementosById(List<BigDecimal> idDireccionDetalladaList,
            BigDecimal centroPobladoId, DrDireccion direccionTabulada) throws ApplicationException {
        return cmtDireccionDetalle.buscarDireccionDetalladaNivelesComplementosById(idDireccionDetalladaList, centroPobladoId, direccionTabulada);
    }
    
    @Override
    public List<CmtDireccionDetalladaMgl> buscarDireccionDetalladaNivelesComplementosByDireccionTexto(BigDecimal centroPobladoId, DrDireccion direccionTabulada, String direccionTexto) throws ApplicationException {
        return cmtDireccionDetalle.buscarDireccionDetalladaNivelesComplementosByDireccionTexto(centroPobladoId, direccionTabulada, direccionTexto);
    }
    
    @Override
    public List<CmtDireccionDetalladaMgl> busquedaDireccionTextoRespuestaGeo(String direccionTexto,
            DrDireccion direccionTabulada, BigDecimal centroPobladoId) throws ApplicationException {
        return cmtDireccionDetalle.busquedaDireccionTextoRespuestaGeo(direccionTexto,
                direccionTabulada, centroPobladoId);
    }
    
    /**
     * Diana -> metodo para agregar Marcas - Hilo
     *
     * @param listaModificacion
     * @param usuario
     * @param nombreArchivo
     * @param perfil
     */
    @Override
    public void actualizarCargueFraudesMasivoHHPP( List<FraudesHHPPMasivoDto> listaModificacion, String usuario, int perfil, String nombreArchivo) {
        Thread thread = new Thread(new CmtRunableFraudesMasivoHHPPDtoManager(listaModificacion,perfil, usuario, nombreArchivo));
        thread.start();
    }
    
    /**
     * Diana -> metodo para remover Marcas - Hilo
     *
     * @param listaModificacion
     * @param usuario
     * @param nombreArchivo
     * @param perfil
     */
    @Override
    public void actualizarCargueFraudesMasivoHHPPDesmarcado( List<FraudesHHPPDesmarcadoMasivoDto> listaModificacion, String usuario, int perfil, String nombreArchivo) {
        Thread thread = new Thread(new CmtRunableFraudesMasivoHHPPDesmarcadoDtoManager(listaModificacion,perfil, usuario, nombreArchivo));
        thread.start();
    }
    
    @Override
    public List<CmtDireccionDetalladaMgl> buscarDireccionTabuladaCompletaParcial(DrDireccion direccionTabulada,
            BigDecimal centroPobladoId, int numeroMaximoResults)  throws ApplicationException {
        return cmtDireccionDetalle.buscarDireccionTabuladaCompletaParcial(direccionTabulada, centroPobladoId, numeroMaximoResults);
    }   
    
     @Override
    public int numeroResultadoBusquedaParcialDireccion(String numRow) throws ApplicationException {
        return cmtDireccionDetalle.registroMaximo(numRow);
    }
    
    @Override
    public List<CmtDireccionDetalladaMgl> buscarDireccionDetalladaByBarrioNodo(BigDecimal centroPobladoId, 
            String barrio, String tipoDireccion, String codigoNodo) throws ApplicationException {
        return cmtDireccionDetalle.buscarDireccionDetalladaByBarrioNodo(centroPobladoId, barrio, tipoDireccion, codigoNodo);
    }
    
    
    /**
     * Metodo que fusiona 2 listados de direccion detallada retornando un solo
     * listado sin elementos repetidos
     *
     * @param direccionDetalladaList direcciones tabuladas encontradas
     * @param direccionDetalladaTextoList direcciones texto encontradas
     *
     * @author Juan David Hernandez
     * @return Listado de Direcci&oacute;n Detallada.
     */
    @Override
    public List<CmtDireccionDetalladaMgl> combinarDireccionDetalladaList(List<CmtDireccionDetalladaMgl> direccionDetalladaList,
            List<CmtDireccionDetalladaMgl> direccionDetalladaTextoList) {
        return ( cmtDireccionDetalle.combinarDireccionDetalladaList(direccionDetalladaList, direccionDetalladaTextoList) );
    }
    
        /**
     * Obtiene listado CmtDireccionDetalladaMgl por dirección
     *
     * @param centroPobladoId
     * @param direccionTabulada
     * @author bocanegravm
     * @return
     */
    @Override
    public List<CmtDireccionDetalladaMgl> buscarDireccionTabuladaUnica(BigDecimal centroPobladoId,
            DrDireccion direccionTabulada) {

        return cmtDireccionDetalle.buscarDireccionTabuladaUnica(centroPobladoId, direccionTabulada);
    }
    
    /**
     * Obtiene listado de coberturas por dirección
     *
     * @param direccion
     * @param subDireccionMgl
     * @author bocanegravm
     * @return List<CmtCoverDto>
     */
    @Override
    public List<CmtCoverDto> coberturasDireccion(DireccionMgl direccion,
            SubDireccionMgl subDireccionMgl) throws ApplicationException {

        return cmtDireccionDetalle.coberturasDireccion(direccion,subDireccionMgl);
    }
    
    /**
     * Obtiene Lista de dirección detallada por id de dirección
     *
     * @param dirId id de la dirección
     * @param idDetallada id de la dirección
     * @return List<CmtDireccionDetalladaMgl>
     *
     * @author Victor Bocanegra
     */
    @Override
    public List<CmtDireccionDetalladaMgl> findDireccionDetallaByDirId(BigDecimal dirId,
            BigDecimal idDetallada) {

        return cmtDireccionDetalle.findDireccionDetallaByDirId(dirId,  idDetallada);
    }
    
    /**
     * Obtiene listado de barrios por dirección
     *
     * @param centroPobladoId
     * @param direccionTabulada
     *
     * @author Victor Bocanegra
     * @return
     */
    @Override
    public List<String> findBarriosDireccionTabuladaMer(BigDecimal centroPobladoId,
            DrDireccion direccionTabulada) {
        
      return cmtDireccionDetalle.findBarriosDireccionTabuladaMer(centroPobladoId, direccionTabulada);
    }
    
    /**
     * Metodo para actualizar una direccion detallada con la indicacion
     *
     * @author bocanegravm
     * @param indicacionesRequestDto
     * @return CmtIndicacionesResponseDto respuesta con el proceso de
     * actualizacion de la solicitud
     * @throws ApplicationException
     *
     */
    @Override
    public CmtIndicacionesResponseDto actualizarIndicaciones(CmtIndicacionesRequestDto indicacionesRequestDto){
        
        return cmtDireccionDetalle.actualizarIndicaciones(indicacionesRequestDto);
        
    }

    /**
     * Metodo para consultar la indicacion de una direccion
     *
     * @author bocanegravm
     * @param indicacionesRequestDto
     * @return CmtIndicacionesResponseDto respuesta con el proceso de consulta
     * de la solicitud
     *
     */
    @Override
    public CmtIndicacionesResponseDto consultarIndicaciones(CmtIndicacionesRequestDto indicacionesRequestDto) {
        return cmtDireccionDetalle.consultarIndicaciones(indicacionesRequestDto);
    }

        @Override
    public CmtAddressGeneralResponseDto consultaDireccionExactaTabulada(CmtDireccionRequestDto cmtDireccionRequestDto) {
        CmtDireccionDetalleMglManager cmtDireccionDetalle1 = new CmtDireccionDetalleMglManager();
        try {
            return cmtDireccionDetalle1.consultaDireccionExactaTabulada(cmtDireccionRequestDto);
        } catch (ExceptionDB | CloneNotSupportedException ex) {
            String msgError = "Error en " + ClassUtils.getCurrentMethodName(this.getClass()) + ": " + ex.getMessage();
            LOGGER.error(msgError, ex);
        } catch (ApplicationException ex) {
            String msgError = "Error al consultar dirección exacta" + ex.getMessage();
            LOGGER.error(msgError, ex);
        }
        return null;
    }

    /**
     * Metodo para consultar el ide de mer a partir del id de rr
     *
     * @param consultaIdMerRequestDto
     * @return ConsultaIdMerResponseDto
     * @author Johan Forero
     */
    @Override
    public ConsultaIdMerResponseDto consultaIdDireccionMER(ConsultaIdMerRequestDto consultaIdMerRequestDto) {
        return cmtDireccionDetalle.consultaIdDireccionMER(consultaIdMerRequestDto);
    }

    /**
     * Metodo para consultar el detalle de la direccion tecnica, tecnologias habilitadas
     *
     * @param consultaFactibilidadRequestDto
     * @return ConsultaFactibilidadResponseDto
     * @author Yasser Leon
     */
    @Override
    public ConsultaFactibilidadResponseDto consultaFactibilidad(ConsultaFactibilidadRequestDto consultaFactibilidadRequestDto) {
        return cmtDireccionDetalle.consultaFactibilidad(consultaFactibilidadRequestDto);
    }

    /**
     * Metodo para retener la dirección que se enviara en la consulta del servicio SITIDATA
     *
     * @param direccion Datos de la dirección que se factibiliza
     */
    @Override
    public void setDireccionConsultaNodoSitiData(DrDireccion direccion) {
        cmtDireccionDetalle.setDrDireccionConsultaSitiData(direccion);
    }
	
	 /**
     * Identifica el flujo del metodo ConsultarNodosPorRecurso.
     * Se encarga de validar el request y de integrar los diferentes insumos.
     *
     * @author Hitts - Leidy Montero
     * @param consultarNodosPorRecursoRequest request de la solicitud
     * @return retorna el response del servicio
     */
    @Override
    public CmtAddressResponseDto consultarNodosPorRecurso(ConsultarNodosPorRecursoRequest consultarNodosPorRecursoRequest) {
        return cmtDireccionDetalle.consultarNodosPorRecurso(consultarNodosPorRecursoRequest);
    }
    
    /**
     *  Obtiene datos del Geo por dirección para
     *  validar creacion automatica de Hhpp por visor
     *
     * @param direccion
     * @param centroPoblado
     * @author Johan Gomez
     * @return AddressService
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @Override
    public AddressService consultarGeoDataVisor(DireccionMgl direccion,
            GeograficoPoliticoMgl centroPoblado) throws ApplicationException {

        return cmtDireccionDetalle.consultarGeoDataVisor(direccion,centroPoblado);
    }
}
