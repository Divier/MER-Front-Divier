/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.dtos.FraudesHHPPDesmarcadoMasivoDto;
import co.com.claro.mgl.dtos.FraudesHHPPMasivoDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.DireccionMgl;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.SubDireccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.rest.dtos.*;
import co.com.telmex.catastro.data.AddressService;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author valbuenayf
 */
public interface CmtDireccionDetalleMglFacadeLocal {

    /**
     * valbuenayf Metodo para consultar la direccion por texto o tabulada
     *
     * @param cmtDireccionDetalladaRequestDto
     * @return
     */
    public CmtAddressResponseDto consultarDireccion(CmtDireccionDetalladaRequestDto cmtDireccionDetalladaRequestDto);

    /**
     * Obtiene listado Hhpp por dirección
     *@param direccion la dirección construida que deseamos buscarle hhpp asociados
     *@param ciudadGpo ciudad por la que se desea filtrar
     * @param evaluarEstadoRegistro
     * @param paginaSeleccionada
     * @param maxResults
     * @param busquedaPaginada
     *
     *@return listado de hhpp asociados a la dirección buscada
     *
     *@author Juan David Hernandez
     * @throws ApplicationException
     */
    public List<HhppMgl> findHhppByDireccion(DrDireccion direccion,
            BigDecimal ciudadGpo, boolean evaluarEstadoRegistro, int paginaSeleccionada,
            int maxResults, boolean busquedaPaginada) throws ApplicationException;

    public List<CmtDireccionDetalladaMgl> findDireccionByDireccionDetallada(DrDireccion direccion,
            BigDecimal ciudadGpo, boolean evaluarEstadoRegistro, int paginaSeleccionada,
            int maxResults, boolean consumoDireccionGeneralWs) throws ApplicationException;


    public int countHhppByDireccion(DrDireccion direccion,
            BigDecimal ciudadGpo) throws ApplicationException;

    
    /**
     * Obtiene una dirección detallada por id de dirección y id de la subdireccion
     *@param dirId id de la dirección
     *@param sdirId id de la subdireccion
     *
     *@return dirección detalla encontrada
     *
     *@author Juan David Hernandez
     * @throws ApplicationException
     */
    public List<CmtDireccionDetalladaMgl> findDireccionDetallaByDirIdSdirId(BigDecimal dirId,
            BigDecimal sdirId) throws ApplicationException;

    

    /**
     * valbuenayf Metodo para consultar la direccion por texto o tabulada
     *
     * @param cmtDireccionRequestDto
     * @return
     */
    public CmtAddressGeneralResponseDto ConsultaDireccionGeneral(CmtDireccionRequestDto cmtDireccionRequestDto);


    /**
     * valbuenayf Metodo para buscar una direcciondetallada por el id
     *
     * @param direccionDetalladaId
     * @return
     * @throws ApplicationException
     */
    public CmtDireccionDetalladaMgl buscarDireccionIdDireccion(BigDecimal direccionDetalladaId)
            throws ApplicationException;

     /**
     * Obtiene el numero de direcciones dirección
     *@param direccion la dirección construida que deseamos buscarle hhpp asociados
     *@param ciudadGpo ciudad por la que se desea filtrar 
     * 
     *@return numero total de direcciones
     * 
     *@author Juan David Hernandez
     * @throws ApplicationException
     */
    public int countDireccionByDireccionDetallada(DrDireccion direccion,
            BigDecimal ciudadGpo) throws ApplicationException;
    
    
    /**
     * Realiza la consulta del listado de <b>HHPP</b> por <i>Coordenadas</i>, de
     * acuerdo a un rango de <i>Desviaci&oacute;n</i>.
     *
     * @param coordenadasDto DTO que contiene la informaci&oacute;n de las
     * Coordenadas y su Desviaci&oacute;n.
     * @return Listado de entidad Direcci&oacute;n Detallada.
     * @throws ApplicationException
     */
    public List<CmtDireccionDetalladaMgl> findDireccionDetalladaByCoordenadas(CmtRequestHhppByCoordinatesDto coordenadasDto)
            throws ApplicationException;
    
    
    /**
     * Obtiene listado de HHPP por coordenada a un rango de desviaci&oacute;n.
     *
     * @author Juan David Hernandez
     * @param coordenadasDto DTO que contiene la informaci&oacute;n de las 
     * Coordenadas y su Desviaci&oacute;n.
     * @return DTO de Respuesta de Direcci&oacute;n General.
     * @throws ApplicationException
     */
    public CmtAddressGeneralResponseDto findDireccionByCoordenadas(CmtRequestHhppByCoordinatesDto coordenadasDto)
            throws ApplicationException;

    public DrDireccion parseCmtDireccionDetalladaMglToDrDireccion(CmtDireccionDetalladaMgl direccionDetMgl);
    
      /**
     * obtiene listado de direcciones detalladas por Drdireccion
     *
     * @author cardenaslb
     * @param drDir
     * @param centropoblado
     * @return
     * @throws ApplicationException
     *
     */
     public List<CmtDireccionDetalladaMgl> findDireccionDetalladaByDrDireccion(DrDireccion drDir, BigDecimal centropoblado)  throws ApplicationException;
     
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
     public CmtDireccionDetalladaMgl findByHhPP(HhppMgl hhppMgl) throws ApplicationException;
     
     public CmtDireccionDetalladaMgl findById(BigDecimal idDireccionDetallada)
            throws ApplicationException;
     
    
     /**
     * Encuentra una dirección por la columna direccion texto, la direccion debe ser la que entrega el geo
     * si es CK o si es Bm o IT la que es estandarizada
     * 
     * @param direccionTexto
     * @param ciudadGpo
     * @param resultadoUnico true cuando se quiere encontrar un unico resultado por texto
     *@return direcciones detallas que cumplan el criterio de busqueda
     * 
     *@author Juan David Hernandez
     * @throws ApplicationException
     */
    public List<CmtDireccionDetalladaMgl> findDireccionDetalladaByDireccionTexto(String direccionTexto,
            BigDecimal ciudadGpo, boolean resultadoUnico, String barrio, String tipoDireccion) throws ApplicationException;
    
    public List<CmtDireccionDetalladaMgl> buscarDireccionDetalladaNivelesComplementosById(List<BigDecimal> idDireccionDetalladaList,
            BigDecimal centroPobladoId, DrDireccion direccionTabulada) throws ApplicationException;

    public List<CmtDireccionDetalladaMgl> buscarDireccionDetalladaNivelesComplementosByDireccionTexto(BigDecimal centroPobladoId, 
            DrDireccion direccionTabulada, String direccionTexto) throws ApplicationException;
    
     public List<CmtDireccionDetalladaMgl> busquedaDireccionTextoRespuestaGeo(String direccionTexto,
            DrDireccion direccionTabulada, BigDecimal centroPobladoId) throws ApplicationException;

    /**
     * Diana -> metodo para agregar Marcas - Hilo
     *
     * @param listaModificacion
     * @param usuario
     * @param nombreArchivo
     * @param perfil
     */
    public void actualizarCargueFraudesMasivoHHPP(List<FraudesHHPPMasivoDto> listaModificacion, String usuario, int perfil, String nombreArchivo);
    
    /**
     * Diana -> metodo para remover Marcas - Hilo
     *
     * @param listaModificacion
     * @param usuario
     * @param nombreArchivo
     * @param perfil
     */
    public void actualizarCargueFraudesMasivoHHPPDesmarcado(List<FraudesHHPPDesmarcadoMasivoDto> listaModificacion, String usuario, int perfil, String nombreArchivo);

    public List<CmtDireccionDetalladaMgl> buscarDireccionTabuladaCompletaParcial(DrDireccion direccionTabulada,
            BigDecimal centroPobladoId, int numeroMaximoResults) throws ApplicationException;

    public int numeroResultadoBusquedaParcialDireccion(String numRow) throws ApplicationException;
    
    public List<CmtDireccionDetalladaMgl> buscarDireccionDetalladaByBarrioNodo(BigDecimal centroPobladoId, 
            String barrio, String tipoDireccion, String codigoNodo)throws ApplicationException;

    
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
    public List<CmtDireccionDetalladaMgl> combinarDireccionDetalladaList(List<CmtDireccionDetalladaMgl> direccionDetalladaList,
            List<CmtDireccionDetalladaMgl> direccionDetalladaTextoList);
    
        /**
     * Obtiene listado CmtDireccionDetalladaMgl por dirección
     *
     * @param centroPobladoId
     * @param direccionTabulada
     * @author bocanegravm
     * @return
     */
    List<CmtDireccionDetalladaMgl> buscarDireccionTabuladaUnica(BigDecimal centroPobladoId,
            DrDireccion direccionTabulada);
    
    /**
     * Obtiene listado de coberturas por dirección
     *
     * @param direccion
     * @param subDireccionMgl
     * @author bocanegravm
     * @return List<CmtCoverDto>
     */
    List<CmtCoverDto> coberturasDireccion(DireccionMgl direccion,
            SubDireccionMgl subDireccionMgl) throws ApplicationException;
    
    /**
     * Obtiene Lista de dirección detallada por id de dirección
     *
     * @param dirId id de la dirección
     * @param idDetallada id de la dirección
     * @return List<CmtDireccionDetalladaMgl>
     *
     * @author Victor Bocanegra
     */
    List<CmtDireccionDetalladaMgl> findDireccionDetallaByDirId(BigDecimal dirId,
            BigDecimal idDetallada);
    
    /**
     * Obtiene listado de barrios por dirección
     *
     * @param centroPobladoId
     * @param direccionTabulada
     *
     * @author Victor Bocanegra
     * @return
     */
     List<String> findBarriosDireccionTabuladaMer(BigDecimal centroPobladoId,
            DrDireccion direccionTabulada);
     
         
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
    CmtIndicacionesResponseDto actualizarIndicaciones(CmtIndicacionesRequestDto indicacionesRequestDto);

    /**
     * Metodo para consultar la indicacion de una direccion
     *
     * @author bocanegravm
     * @param indicacionesRequestDto
     * @return CmtIndicacionesResponseDto respuesta con el proceso de consulta
     * de la solicitud
     * @throws ApplicationException
     *
     */
    CmtIndicacionesResponseDto consultarIndicaciones(CmtIndicacionesRequestDto indicacionesRequestDto);
    
     /**
     * Metodo para consultar la indicacion de una direccion
     *
     * @author Juan David Hernandez
     * @param indicacionesRequestDto
     * @return CmtIndicacionesResponseDto respuesta con el proceso de consulta
     * de la solicitud
     * @throws ApplicationException
     *
     */
    public CmtAddressGeneralResponseDto consultaDireccionExactaTabulada(CmtDireccionRequestDto cmtDireccionRequestDto);

    /**
     * Metodo para consultar el ide de mer a partir del id de rr
     *
     * @author Johan Forero
     * @param ConsultaIdMerRequestDto
     * @return ConsultaIdMerResponseDto
     */
    ConsultaIdMerResponseDto consultaIdDireccionMER(ConsultaIdMerRequestDto consultaIdMerRequestDto);

    /**
     *  Metodo para consultar la direccion con el numero de cuenta
     *
     * @author Manuel Hernández Rivas
     * @param nodeIDsByAccountRequestDto
     * @return CmtAddressResponseDto respuesta con el proceso de consulta
     * de la solicitud
     *
     */
    public CmtAddressResponseDto getNodeIDsByAccount(getNodeIDsByAccountRequestDto nodeIDsByAccountRequestDto);

    /**
     * Metodo para consultar el detalle de la direccion tecnica, tecnologias habilitadas
     *
     * @param consultaFactibilidadRequestDto
     * @return ConsultaFactibilidadResponseDto
     * @author Yasser Leon
     */
    ConsultaFactibilidadResponseDto consultaFactibilidad(ConsultaFactibilidadRequestDto consultaFactibilidadRequestDto);

    /**
     * Metodo para retener la dirección que se enviara en la consulta del servicio SITIDATA
     *
     * @param direccion Datos de la dirección que se factibiliza
     */
     void setDireccionConsultaNodoSitiData(DrDireccion direccion);
	 
	 /**
     * Metodo para mostrar la direccion por tecnologia
     *
     * @author Leidy Montero
     * @param consultarNodosPorRecursoRequest ingreso de parametros desde el request
     * @return CmtAddressResponseDto detalle de la direccion
     *
     */
    public CmtAddressResponseDto consultarNodosPorRecurso(ConsultarNodosPorRecursoRequest consultarNodosPorRecursoRequest);
    
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
    AddressService consultarGeoDataVisor(DireccionMgl direccion,
            GeograficoPoliticoMgl centroPoblado) throws ApplicationException;
}
