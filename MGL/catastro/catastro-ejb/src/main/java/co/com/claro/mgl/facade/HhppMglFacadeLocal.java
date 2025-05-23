package co.com.claro.mgl.facade;

import co.com.claro.mgl.dtos.CmtDefaultBasicResponse;
import co.com.claro.mgl.dtos.CmtEstadoHhppDto;
import co.com.claro.mgl.dtos.CmtPestaniaHhppDto;
import co.com.claro.mgl.dtos.EstratoxTorreDto;
import co.com.claro.mgl.dtos.FiltroBusquedaDirecccionDto;
import co.com.claro.mgl.dtos.FiltroConsultaHhppDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.DireccionMgl;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.MarcasMgl;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.NotasAdicionalesMgl;
import co.com.claro.mgl.jpa.entities.PaginacionDto;
import co.com.claro.mgl.jpa.entities.SubDireccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionSolicitudMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.visitasTecnicas.entities.CityEntity;
import co.com.telmex.catastro.data.UnidadByInfo;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface HhppMglFacadeLocal extends BaseFacadeLocal<HhppMgl> {

    void setUser(String user, int perfil);

    List<HhppMgl> findHhppByCodNodo(String nodoHhpp) throws ApplicationException;

    List<HhppMgl> findHhppBySelect(BigDecimal geo, BigDecimal blackLis, String tipoUnidad, String estadoUnidad, String calle, String placa, String apartamento, BigDecimal hhppId, NodoMgl nodo, Date fechaCreacion, String comunidad, String division) throws ApplicationException;

    List<HhppMgl> findHhppByIdRR(String HhpIdrR) throws ApplicationException;

    List<HhppMgl> findHhppBySubEdificioId(BigDecimal HhppSubEdificioId) throws ApplicationException;

    PaginacionDto<HhppMgl> findBySubOrCM(int paginaSelected, int maxResults, CmtSubEdificioMgl cmtSubEdificioMgl,
            FiltroConsultaHhppDto filtroConsultaHhppDto, Constant.FIND_HHPP_BY findBy) throws ApplicationException;

    String getInstalledServises(String cta, String comunidad, String division) throws ApplicationException;

    List<AuditoriaDto> construirAuditoria(HhppMgl hhppMgl)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;

    HhppMgl actualizarNodoHhpp(HhppMgl hhppMgl, String solicitud, String tipoSolicitud, String usuario) throws ApplicationException;

    List<UnidadByInfo> getHhppByCoordinates(String longitude, String latitude, int deviationMtr, int unitsNumber, String ciudad);

    List<UnidadByInfo> getHhppByDireccion(String direccion, String comunidad, String barrio);

    /**
     * Agregar marcas a un HHPP existente.Permite agregar uno o varias marcas a
        un hhpp existente en la plataforma
     *
     * @author Luis Alejandro Rodriguez
     * @version 1.0 revision .
     * @param hhppMgl objeto con el id del hhpp
     * @param listaMarcasMgl
     * @param usuario
     * @return Una respuesta basica para evidenciar si pudo o no realizar la
     * tarea
     */
    CmtDefaultBasicResponse agregarMarcasHhpp(HhppMgl hhppMgl, List<MarcasMgl> listaMarcasMgl, String usuario);
    
    CmtDefaultBasicResponse agregarMarcasHhppFichasNodos(HhppMgl hhppMgl, List<MarcasMgl> listaMarcasMgl);

    /**
     * eliminar marcas a un HHPP existente.Permite eliminar uno o varias marcas
        a un hhpp existente en la plataforma
     *
     * @author Luis Alejandro Rodriguez
     * @version 1.0 revision .
     * @param hhppMgl objeto con el id del hhpp
     * @param listaMarcasMgl
     * @return Una respuesta basica para evidenciar si pudo o no realizar la
     * tarea
     */
    CmtDefaultBasicResponse eliminarMarcasHhpp(HhppMgl hhppMgl, List<MarcasMgl> listaMarcasMgl);

    /**
     * Agregar notas a un HHPP existente. Permite agregar uno o varias notas a
     * un hhpp existente en la plataforma
     *
     * @author Luis Alejandro Rodriguez
     * @version 1.0 revision .
     * @param hhppMgl objeto con el id del hhpp
     * @param listaNotasHhppMgls lista de notas que se desean agregar al hhpp
     * @return Una respuesta basica para evidenciar si pudo o no realizar la
     * tarea
     */
    CmtDefaultBasicResponse agregarNotasHhpp(HhppMgl hhppMgl, List<NotasAdicionalesMgl> listaNotasHhppMgls);

    /**
     * Valida si Existe la unidad.Función utilizada para validar si la unidad existe
     *
     * @param cityEntity objeto en el que viene cargada la direccion que se
     * desea validar su existencia
     * @param centroPobladoId id del centro poblado en el que se encuentra la
     * dirección.
     * @param tipoAccion tipo de accion de la solicitud para validar. 3.
     * reactivación.
     * @param tecnologiaBasicaId
     *
     * @return verdarero si la unidad Existe en Repositorio
     *
     * @author Juan David Hernandez
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    HhppMgl validaExistenciaHhpp(
            CityEntity cityEntity,
            BigDecimal centroPobladoId, String tipoAccion,
            BigDecimal tecnologiaBasicaId) throws ApplicationException;

    List<HhppMgl> findByNodoMgl(NodoMgl nodoMgl) throws ApplicationException;

    HhppMgl findById(BigDecimal idHhpp) throws ApplicationException;

    int countHhpp(BigDecimal idCiudad, BigDecimal idCentroPoblado,
            BigDecimal idNodo, BigDecimal tipoTecnologia,
            String nombreBasica, Object valorAtributo,
            Date fechaInicial, Date fechaFinal, BigDecimal idHhpp, 
            boolean inhabilitar, boolean creadoXFicha,BigDecimal etiqueta,
            BigDecimal idCcmmMgl, BigDecimal idCcmmRr)
            throws ApplicationException;

    /**
     * Solicitar la busqueda Hhpp Solicitar la busqueda de los hhpp que
     * correspondan según los filtros como mínimo debe ser diferente de null la
     * ciudad y el tipo de tecnología.
     *
     * @author becerraarmr
     * @param idCiudad ciudad de geográfico
     * @param idCentroPoblado el centro poblado
     * @param idNodo un nodo de la ciudad o del centro poblado
     * @param tipoTecnologia el tipo de tecnología relacionadas
     * @param nombreBasica nombre del atributo del hhpp
     * @param valorAtributo valor del atributo a buscar, puede ser String y
     * DrDirecciones
     * @param fechaInicial
     * @param rango el rango de busqueda del valor.
     * @param fechaFinal
     * @param idHhpp id del hhpp
     * @param inhabilitar
     * @param creadoXFicha
     * @param etiqueta
     * @param idCcmmMgl
     * @param idCcmmRr
     * @return el entero con el valor encontrado
     * @throws ApplicationException si hay algún error en la busqueda
     */
     List<HhppMgl> findHhpp(BigDecimal idCiudad, BigDecimal idCentroPoblado,
            BigDecimal idNodo, BigDecimal tipoTecnologia, String nombreBasica,
            Object valorAtributo, Date fechaInicial, Date fechaFinal, int[] rango,
            BigDecimal idHhpp, boolean inhabilitar,
            boolean creadoXFicha,BigDecimal etiqueta,  BigDecimal idCcmmMgl, BigDecimal idCcmmRr) throws ApplicationException;

    /**
     * Obtiene listado de Hhpp por Suscriptor
     *
     * @param suscriptor por el cual se desea filtrar hhpp
     * @return listado de hhpp
     * @author Juan David Hernandez Torres
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    List<HhppMgl> findHhppBySuscriptor(String suscriptor)
            throws ApplicationException;

    /**
     * Consulta de hhpp existentes por cuenta matriz
     *
     * @param cuentaMatrizMgl {@link CmtCuentaMatrizMgl} cuenta matriz a
     * consultar
     * @return {@link Integer} Cantidad de registros encontrados
     */
    Integer cantidadHhppCuentaMatriz(CmtCuentaMatrizMgl cuentaMatrizMgl);

    /**
     * Consulta de n&uacute;mero de HHPP activos
     *
     * @param cuentaMatrizMgl {@link CmtCuentaMatrizMgl} Cuenta matriz a
     * verificar
     * @return {@link Integer} Cantidad de registros activos encontrados
     * @throws ApplicationException Error lanzado al realizar consultas
     */
    Integer cantidadHhppEnServicio(CmtCuentaMatrizMgl cuentaMatrizMgl) throws ApplicationException;

    /**
     * Metodo para realizar borrado logico de un HHPP
     *
     * @param cuentaMatrizMgl {@link CmtCuentaMatrizMgl} cuenta matriz con los
     * HHPP a eliminar
     * @return {@link List}&lt;{@link HhppMgl} listado de HHPP que no se van a
     * eliminar
     * @throws ApplicationException Excepci&oacute;n lanzanda durante el proceso
     */
    List<HhppMgl> eliminarHhppCuentaMatriz(CmtCuentaMatrizMgl cuentaMatrizMgl) throws ApplicationException;

    /**
     * Cambiar el estado de un HHPP. Permite realizar el cambio de estado de
     * hhpp teniendo en cuenta reglas para los cambios de estado
     *
     * @author Luis Alejandro Rodriguez
     * @version 1.0 revision .
     * @param hhppMgl. recibe el id del hhpp que se desea modificar
     * @param estadoHhpp objeto estado hhpp para realizar el cambio
     * @return Retorna el objeto estado hhpp que quedo asignado al hhpp
     */
    CmtDefaultBasicResponse cambioEstadoHhpp(HhppMgl hhppMgl, CmtEstadoHhppDto estadoHhpp);


    CmtPestaniaHhppDto findByHhppSubOrCM(FiltroBusquedaDirecccionDto filtroBusquedaDirecccionDto, int paginaSelected,
            int maxResults, CmtSubEdificioMgl cmtSubEdificioMgl,
            Constant.FIND_HHPP_BY findBy) throws ApplicationException;

    int countListHhppCM(FiltroBusquedaDirecccionDto filtroBusquedaDirecccionDto, CmtSubEdificioMgl cmtSubEdificioMgl) throws ApplicationException;

    int countListHhppSubEdif(FiltroBusquedaDirecccionDto filtroBusquedaDirecccionDto, CmtSubEdificioMgl cmtSubEdificioMgl)
            throws ApplicationException;

    List<EstratoxTorreDto> findByHhppEstratoSubCM(CmtSubEdificioMgl cmtSubEdificioMgl,
            Constant.FIND_HHPP_BY findBy) throws ApplicationException;

    /**
     * Autor: Victor Bocanegra
     *
     * @param cmtDireccionSolicitudMgl
     * @param cmtBasicaMgl
     * @param consultaUnidades
     * @return List<NodoMgl> lista de nodos cercanos.
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    List<NodoMgl> retornaNodosCercano(CmtDireccionSolicitudMgl cmtDireccionSolicitudMgl,
            CmtBasicaMgl cmtBasicaMgl, boolean consultaUnidades) throws ApplicationException;

    /**
     * valbuenayf Metodo para buscar los hhpp de un id direccion
     *
     * @param idDireccion
     * @return
     */
    List<HhppMgl> findHhppDireccion(BigDecimal idDireccion);

    /*
     * @param dirId id de la dirección
     * @return listado de hhpp 
     * @author Juan David Hernandez Torres
     */
    List<HhppMgl> findHhppByDirId(BigDecimal dirId)
            throws ApplicationException;

    /**
     * Autor: Juan David Hernandez
     *
     * @param direccionDetalladaMgl
     * @param cmtDireccionSolicitudMgl
     * @param cmtBasicaMgl
     * @param centroPobladoId
     * @return List<NodoMgl> lista de nodos cercanos.
     * @throws co.com.claro.mgl.error.ApplicationException
     */
     List<NodoMgl> obtenerNodosCercanoSolicitudHhpp(CmtDireccionDetalladaMgl direccionDetalladaMgl,
            CmtBasicaMgl cmtBasicaMgl, BigDecimal centroPobladoId) throws ApplicationException;

    /**
     * valbuenayf Metodo para buscar los hhpp de un id subDireccion
     *
     * @param idSubDireccion
     * @return
     */
    List<HhppMgl> findHhppSubDireccion(BigDecimal idSubDireccion);
    
    /**
     * valbuenayf Metodo para buscar los hhpp de un id subDireccion
     *
     * @param HhpCalle
     * @param HhpPlaca
     * @param HhpComunidad
     * @return
     */
    List<HhppMgl> findHhppNap(String HhpCalle, String HhpPlaca,String HhpComunidad);
    
    /**
     * Metodo para buscar los hhpp apartir de una direccion y una subdireccion
     *
     * @author Orlando Velasquez
     * @param dir
     * @param subDir
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    List<HhppMgl> findByDirAndSubDir(DireccionMgl dir, SubDireccionMgl subDir) throws ApplicationException;
    
    HhppMgl validaExistenciaHhppFichas(
            DrDireccion drDireccion,
            BigDecimal centroPobladoId, String tipoAccion, BigDecimal tecnologiaBasicaId) throws ApplicationException ;    
    
   List<HhppMgl> obtenerHhppByDireccionDetallaList(List <CmtDireccionDetalladaMgl> direccionDetalladaList) throws ApplicationException;
   
        /**
     * Metodo para obtener listado de hhpp por el codigo del nodo
     *
     * @author Juan David Hernandez
     * @param codigoNodo
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    List<HhppMgl> findHhppByNodo(String codigoNodo) throws ApplicationException;
   
   
   /**
     * Realiza la b&uacute;squeda de HHPP activos a trav&eacute;s de su Direcci&oacute;n y su SubDirecci&oacute;n.
     * 
     * @param idNodo Identificador del Nodo.
     * @param idDireccion Identificador de la Direcci&oacute;n.
     * @param idSubDireccion Identificador de la SubDirecci&oacute;n.
     * @return Listado de HHPP coincidente con la Direcci&oacute;n y su SubDirecci&oacute;n.
     * @throws ApplicationException 
     */
     List<HhppMgl> findHhppByNodoDireccionYSubDireccion(BigDecimal idNodo, BigDecimal idDireccion, BigDecimal idSubDireccion) 
            throws ApplicationException;
    
        /**
     * Realiza la b&uacute;squeda de HHPP activos a trav&eacute;s de una cuenta
     * matriz
     *
     * @param cuentaMatriz Identificador del Nodo.
     * @return Listado de HHPP coincidente con la cuenta matriz.
     * @throws ApplicationException
     */
    List<HhppMgl> obtenerHhppCM(CmtCuentaMatrizMgl cuentaMatriz) throws ApplicationException;
    
    /**
     * Realiza la b&uacute;squeda de HHPP activos unica direccion y subdireccion
     * no repetidos por varias tecnologias .
     *
     * @param cuentaMatriz
     * @return Listado de HHPP
     * @throws ApplicationException
     */
    List<HhppMgl> obtenerHhppCMUnicaDirAndSub(CmtCuentaMatrizMgl cuentaMatriz) throws ApplicationException;
    
        /**
     * Realiza la b&uacute;squeda de HHPP activos unica direccion y subdireccion
     * no repetidos por varias tecnologias por id subedificio .
     *
     * @param HhppSubEdificioId
     * @return Listado de HHPP
     * @throws ApplicationException
     */
     List<HhppMgl> findHhppBySubEdificioIdUnicaDirAndSub(BigDecimal HhppSubEdificioId);
     
         /**
     * Realiza la b&uacute;squeda de HHPP activos a trav&eacute;s de su Nodo,
     * Direcci&oacute;n y SubDirecci&oacute;n. Autor: Victor Bocanegra
     *
     * @param idNodo Identificador del Nodo.
     * @param idDireccion Identificador de la Direcci&oacute;n.
     * @param idSubDireccion Identificador de la SubDirecci&oacute;n.
     * @return Listado de HHPP coincidente con el Nodo, Direcci&oacute;n y
     * SubDirecci&oacute;n.
     * @throws ApplicationException
     */
     List<HhppMgl> findHhppByNodoDireccionAndSubDireccion
        (BigDecimal idNodo, BigDecimal idDireccion, BigDecimal idSubDireccion) throws ApplicationException;
        
        
             /**
     * Realiza la b&uacute;squeda de HHPP por direccion y subdireccion
     * . Autor: cardenaslb
     *
     * @param idNodo Identificador del Nodo.
     * @param idDireccion Identificador de la Direcci&oacute;n.
     * @param idSubDireccion Identificador de la SubDirecci&oacute;n.
     * @return Listado de HHPP coincidente con el Nodo, Direcci&oacute;n y
     * SubDirecci&oacute;n.
     * @throws ApplicationException
     */
     List<HhppMgl> findHhppByDirIdSubDirId
        (BigDecimal idDireccion, BigDecimal idSubDireccion) throws ApplicationException;
}
