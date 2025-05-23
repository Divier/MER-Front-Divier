package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.HhppMglManager;
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
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.visitasTecnicas.entities.CityEntity;
import co.com.telmex.catastro.data.UnidadByInfo;
import co.com.telmex.catastro.services.ws.initialize.Initialized;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Admin
 */
@Stateless
public class HhppMglFacade implements HhppMglFacadeLocal {

    private static final Logger LOGGER = LogManager.getLogger(HhppMglFacade.class);
    private final HhppMglManager hhppMglManager;
    private String user;
    private Integer perfil;

    public HhppMglFacade() {
        this.hhppMglManager = new HhppMglManager();
        this.user = "";
        this.perfil = 0;
    }

    @Override
    public void setUser(String user, int perfil) {
        this.user = user;
        this.perfil = perfil;
    }

    @Override
    public List<HhppMgl> findAll() throws ApplicationException {
        return hhppMglManager.findAll();
    }

    @Override
    public HhppMgl create(HhppMgl hhppMgl) throws ApplicationException {
        return hhppMglManager.create(hhppMgl);
    }

    @Override
    public HhppMgl update(HhppMgl hhppMgl) throws ApplicationException {
        return hhppMglManager.update(hhppMgl);
    }

    @Override
    public boolean delete(HhppMgl hhppMgl) throws ApplicationException {
        return hhppMglManager.delete(hhppMgl);
    }

    @Override
    public HhppMgl findById(BigDecimal idHhpp) throws ApplicationException {
        return hhppMglManager.findById(idHhpp);
    }

    @Override
    public List<HhppMgl> findHhppByCodNodo(String nodoHhpp) throws ApplicationException {
        return hhppMglManager.findHhppByCodNodo(nodoHhpp);
    }

    @Override
    public HhppMgl findById(HhppMgl sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<HhppMgl> findHhppBySelect(BigDecimal geo, BigDecimal blackLis, String tipoUnidad, String estadoUnidad, String calle, String placa, String apartamento, BigDecimal hhppId, NodoMgl nodo, Date fechaCreacion, String comunidad, String division) throws ApplicationException {
        return hhppMglManager.findHhppBySelect(geo, blackLis, tipoUnidad, estadoUnidad, calle, placa, apartamento, hhppId, nodo, fechaCreacion, comunidad, division);
    }

    @Override
    public List<HhppMgl> findHhppByIdRR(String HhpIdrR) throws ApplicationException {
        return hhppMglManager.findHhppByIdRR(HhpIdrR);
    }

    @Override
    public HhppMgl actualizarNodoHhpp(HhppMgl hhppMgl, String solicitud, String tipoSolicitud, String usuario) throws ApplicationException {
        return hhppMglManager.actualizarNodoHhpp(hhppMgl, solicitud, tipoSolicitud, usuario);
    }

    @Override
    public List<HhppMgl> findHhppBySubEdificioId(BigDecimal HhppSubEdificioId) throws ApplicationException {
        return hhppMglManager.findHhppBySubEdificioId(HhppSubEdificioId);
    }

    @Override
    public PaginacionDto<HhppMgl> findBySubOrCM(int paginaSelected, int maxResults, CmtSubEdificioMgl cmtSubEdificioMgl,
            FiltroConsultaHhppDto filtroConsultaHhppDto, Constant.FIND_HHPP_BY findBy) throws ApplicationException {
        return hhppMglManager.findBySubOrCM(paginaSelected, maxResults, cmtSubEdificioMgl, filtroConsultaHhppDto, findBy);
    }

    @Override
    public String getInstalledServises(String cta, String comunidad, String division) throws ApplicationException {
        return hhppMglManager.getInstalledServises(cta, comunidad, division);
    }

    @Override
    public List<AuditoriaDto> construirAuditoria(HhppMgl hhppMgl)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (hhppMgl != null) {
            return hhppMglManager.construirAuditoria(hhppMgl);
        } else {
            return null;
        }
    }

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
    @Override
    public CmtDefaultBasicResponse agregarMarcasHhpp(HhppMgl hhppMgl, List<MarcasMgl> listaMarcasMgl,
            String usuario) {
        Initialized.getInstance();
        return hhppMglManager.agregarMarcasHhpp(hhppMgl, listaMarcasMgl, usuario);
    }
    
    @Override
    public CmtDefaultBasicResponse agregarMarcasHhppFichasNodos(HhppMgl hhppMgl, List<MarcasMgl> listaMarcasMgl) {
        Initialized.getInstance();
        return hhppMglManager.agregarMarcasHhppFichasNodos(hhppMgl, listaMarcasMgl);
    }
    
    

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
    @Override
    public CmtDefaultBasicResponse eliminarMarcasHhpp(HhppMgl hhppMgl, List<MarcasMgl> listaMarcasMgl) {
        Initialized.getInstance();
        return hhppMglManager.eliminarMarcasHhpp(hhppMgl, listaMarcasMgl);
    }

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
    @Override
    public CmtDefaultBasicResponse agregarNotasHhpp(HhppMgl hhppMgl, List<NotasAdicionalesMgl> listaNotasHhppMgls) {
        Initialized.getInstance();
        return hhppMglManager.agregarNotasHhpp(hhppMgl, listaNotasHhppMgls);
    }

    @Override
    public List<UnidadByInfo> getHhppByCoordinates(String longitude, String latitude, int deviationMtr, int unitsNumber, String ciudad) {
        return hhppMglManager.getHhppByCoordinates(longitude, latitude, deviationMtr, unitsNumber, ciudad);
    }

    @Override
    public List<UnidadByInfo> getHhppByDireccion(String direccion, String comunidad, String barrio) {
        return hhppMglManager.getHhppByDireccion(direccion, comunidad, barrio);
    }

    /**
     * Valida si Existe la unidad.Función utilizada para validar si la unidad
        existe
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
    @Override
    public HhppMgl validaExistenciaHhpp(
            CityEntity cityEntity,
            BigDecimal centroPobladoId, String tipoAccion, BigDecimal tecnologiaBasicaId) throws ApplicationException {
        return hhppMglManager.validaExistenciaHhpp(
                cityEntity, centroPobladoId, tipoAccion, tecnologiaBasicaId);
    }
    
    @Override
    public HhppMgl validaExistenciaHhppFichas(
            DrDireccion drDireccion,
            BigDecimal centroPobladoId, String tipoAccion, BigDecimal tecnologiaBasicaId) throws ApplicationException {
        return hhppMglManager.validaExistenciaHhppFichas(drDireccion, centroPobladoId, tipoAccion, tecnologiaBasicaId);
    }
    

    @Override
    public List<HhppMgl> findByNodoMgl(NodoMgl nodoMgl) throws ApplicationException {
        return hhppMglManager.findByNodoMgl(nodoMgl);
    }

    /**
     * Solicitar Contar Hhpp Se solicita al manager contar los hhpp que
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
     * @param fechaInicial - fecha inicial para inhabilitar
     * @param fechaFinal - fecha final para inhabilitar.
     * @param idHhpp id del hhpp
     * @param inhabilitar
     * @param creadoXFicha
     * @param etiqueta
     * @param idCcmmMgl
     * @param idCcmmRr
     * @return el entero con el valor encontrado
     * @throws ApplicationException si hay algún error en la busqueda
     */
    @Override
    public int countHhpp(BigDecimal idCiudad, BigDecimal idCentroPoblado,
            BigDecimal idNodo, BigDecimal tipoTecnologia,
            String nombreBasica, Object valorAtributo, 
            Date fechaInicial, Date fechaFinal, BigDecimal idHhpp, 
            boolean inhabilitar, boolean creadoXFicha,BigDecimal etiqueta, 
             BigDecimal idCcmmMgl, BigDecimal idCcmmRr)
            throws ApplicationException {
        
        return hhppMglManager.countHhpp(idCiudad, idCentroPoblado, idNodo, tipoTecnologia,
                nombreBasica, valorAtributo, fechaInicial, fechaFinal, 
                idHhpp, inhabilitar,creadoXFicha,etiqueta, 
                idCcmmMgl, idCcmmRr);
    }

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
    @Override
    public List<HhppMgl> findHhpp(BigDecimal idCiudad, BigDecimal idCentroPoblado,
            BigDecimal idNodo,
            BigDecimal tipoTecnologia, String nombreBasica,
            Object valorAtributo, Date fechaInicial,
            Date fechaFinal, int[] rango, BigDecimal idHhpp, 
            boolean inhabilitar, boolean creadoXFicha, BigDecimal etiqueta, BigDecimal idCcmmMgl, BigDecimal idCcmmRr) throws ApplicationException {
        
        return hhppMglManager.findHhpp(idCiudad, idCentroPoblado, idNodo, tipoTecnologia,
                nombreBasica, valorAtributo, fechaInicial, fechaFinal, rango,
                idHhpp,inhabilitar,creadoXFicha,etiqueta,idCcmmMgl,idCcmmRr);
    }

    /**
     * Cambiar el estado de un HHPP. Permite realizar el cambio de estado de
     * hhpp teniendo en cuenta reglas para los cambios de estado
     *
     * @author Luis Alejandro Rodriguez
     * @version 1.0 revision .
     * @param estadoHhpp objeto estado hhpp para realizar el cambio
     * @return Retorna el objeto estado hhpp que quedo asignado al hhpp
     */
    @Override
    public CmtDefaultBasicResponse cambioEstadoHhpp(HhppMgl hhppMgl, CmtEstadoHhppDto estadoHhpp) {
        return hhppMglManager.cambioEstadoHhpp(hhppMgl, estadoHhpp);
    }



    @Override
    public CmtPestaniaHhppDto findByHhppSubOrCM(FiltroBusquedaDirecccionDto filtroBusquedaDirecccionDto, int paginaSelected, int maxResults, CmtSubEdificioMgl cmtSubEdificioMgl, Constant.FIND_HHPP_BY findBy) throws ApplicationException {
        return hhppMglManager.findByHhppSubOrCM(filtroBusquedaDirecccionDto, paginaSelected, maxResults, cmtSubEdificioMgl, findBy);
    }

    @Override
    public int countListHhppCM(FiltroBusquedaDirecccionDto filtroBusquedaDirecccionDto, CmtSubEdificioMgl cmtSubEdificioMgl) throws ApplicationException {
        return hhppMglManager.countListHhppCM(filtroBusquedaDirecccionDto, cmtSubEdificioMgl);
    }

    @Override
    public int countListHhppSubEdif(FiltroBusquedaDirecccionDto filtroBusquedaDirecccionDto, CmtSubEdificioMgl cmtSubEdificioMgl) throws ApplicationException {
        return hhppMglManager.countListHhppSubEdif(filtroBusquedaDirecccionDto, cmtSubEdificioMgl);
    }

    @Override
    public List<EstratoxTorreDto> findByHhppEstratoSubCM(CmtSubEdificioMgl cmtSubEdificioMgl, Constant.FIND_HHPP_BY findBy) throws ApplicationException {
        return hhppMglManager.findByHhppEstratoSubCM(cmtSubEdificioMgl, findBy);
    }

    /**
     * Consulta de hhpp existentes por cuenta matriz
     *
     * @param cuentaMatrizMgl {@link CmtCuentaMatrizMgl} cuenta matriz a
     * consultar
     * @return {@link Integer} Cantidad de registros encontrados
     */
    @Override
    public Integer cantidadHhppCuentaMatriz(CmtCuentaMatrizMgl cuentaMatrizMgl) {
        return hhppMglManager.cantidadHhppCuentaMatriz(cuentaMatrizMgl);
    }

    /**
     * {@inheritDoc }
     *
     * @param cuentaMatrizMgl {@link CmtCuentaMatrizMgl} Cuenta matriz a
     * verificar
     * @return {@link Integer} Cantidad de registros activos encontrados
     * @throws ApplicationException Error lanzado al realizar consultas
     */
    @Override
    public Integer cantidadHhppEnServicio(CmtCuentaMatrizMgl cuentaMatrizMgl) throws ApplicationException {
        return hhppMglManager.cantidadHhppEnServicio(cuentaMatrizMgl);
    }

    /**
     * {@inheritDoc }
     *
     * @param cuentaMatrizMgl {@link CmtCuentaMatrizMgl} cuenta matriz con los
     * HHPP a eliminar
     * @return {@link List}&lt;{@link HhppMgl} listado de HHPP que no se van a
     * eliminar
     * @throws ApplicationException Excepci&oacute;n lanzanda durante el proceso
     */
    @Override
    public List<HhppMgl> eliminarHhppCuentaMatriz(CmtCuentaMatrizMgl cuentaMatrizMgl) throws ApplicationException {
        return hhppMglManager.eliminarHhppCuentaMatriz(cuentaMatrizMgl, user, perfil);
    }

    /**
     * Autor: Victor Bocanegra
     *
     * @param cmtDireccionSolicitudMgl
     * @param cmtBasicaMgl
     * @param consultaUnidades
ut     * @return List<NodoMgl> lista de nodos cercanos.
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @Override
    public List<NodoMgl> retornaNodosCercano(CmtDireccionSolicitudMgl cmtDireccionSolicitudMgl,
            CmtBasicaMgl cmtBasicaMgl, boolean consultaUnidades) throws ApplicationException {

        return hhppMglManager.retornaNodosCercano(cmtDireccionSolicitudMgl, cmtBasicaMgl, consultaUnidades);
    }

    /**
     * valbuenayf Metodo para buscar los hhpp de un id direccion
     *
     * @param idDireccion
     * @return
     */
    @Override
    public List<HhppMgl> findHhppDireccion(BigDecimal idDireccion) {

        return hhppMglManager.findHhppDireccion(idDireccion);
    }

    /**
     * Obtiene listado de Hhpp por Suscriptor
     *
     * @param suscriptor por el cual se desea filtrar hhpp
     * @return listado de hhpp
     * @author Juan David Hernandez Torres
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @Override
    public List<HhppMgl> findHhppBySuscriptor(String suscriptor)
            throws ApplicationException {
        return hhppMglManager.findHhppBySuscriptor(suscriptor);
    }

    /**
     * Obtiene listado de Hhpp por dirección
     *
     * @param dirId id de la dirección
     * @return listado de hhpp
     * @author Juan David Hernandez Torres
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @Override
    public List<HhppMgl> findHhppByDirId(BigDecimal dirId)
            throws ApplicationException {
        return hhppMglManager.findHhppByDirId(dirId);
    }

    /**
     * Autor: Juan David Hernandez
     *
     * @param cmtBasicaMgl
     * @param centroPobladoId
     * @return List<NodoMgl> lista de nodos cercanos.
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @Override
    public List<NodoMgl> obtenerNodosCercanoSolicitudHhpp(CmtDireccionDetalladaMgl cmtDireccionDetalladaMgl,
            CmtBasicaMgl cmtBasicaMgl, BigDecimal centroPobladoId) throws ApplicationException {
        try {
            return hhppMglManager.obtenerNodosCercanoSolicitudHhpp(cmtDireccionDetalladaMgl, cmtBasicaMgl, centroPobladoId);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg);
        }
    }



    /**
     * valbuenayf Metodo para buscar los hhpp de un id sibDireccion
     *
     * @return
     */
    @Override
    public List<HhppMgl> findHhppSubDireccion(BigDecimal idSubDireccion) {

        return hhppMglManager.findHhppSubDireccion(idSubDireccion);
    }
    
    /**
     * valbuenayf Metodo para buscar los hhpp de un id sibDireccion
     *
     * @return
     */
    @Override
    public List<HhppMgl> findHhppNap(String HhpCalle, String HhpPlaca,String HhpComunidad) {

        return hhppMglManager.findHhppNap(HhpCalle,HhpPlaca,HhpComunidad);
    }

    /**
     * Metodo para buscar los hhpp apartir de una direccion y una subdireccion
     *
     * @author Orlando Velasquez
     * @param dir
     * @param subDir
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @Override
    public List<HhppMgl> findByDirAndSubDir(DireccionMgl dir, SubDireccionMgl subDir) throws ApplicationException {

        return hhppMglManager.findByDirAndSubDir(dir, subDir);
    }
    
    @Override
    public List<HhppMgl> obtenerHhppByDireccionDetallaList(List <CmtDireccionDetalladaMgl> direccionDetalladaList) throws ApplicationException{
       return hhppMglManager.obtenerHhppByDireccionDetallaList(direccionDetalladaList);  
    }
    
     /**
     * Metodo para obtener listado de hhpp por el codigo del nodo
     *
     * @author Juan David Hernandez
     * @param codigoNodo
     * @return
     */
    @Override
    public List<HhppMgl> findHhppByNodo(String codigoNodo) throws ApplicationException {
        return hhppMglManager.findHhppByNodo(codigoNodo);
    }
    
    
    /**
     * Realiza la b&uacute;squeda de HHPP activos a trav&eacute;s de su Direcci&oacute;n y su SubDirecci&oacute;n.
     * 
     * @param idNodo Identificador del Nodo.
     * @param idDireccion Identificador de la Direcci&oacute;n.
     * @param idSubDireccion Identificador de la SubDirecci&oacute;n.
     * @return Listado de HHPP coincidente con la Direcci&oacute;n y su SubDirecci&oacute;n.
     * @throws ApplicationException 
     */
    @Override
    public List<HhppMgl> findHhppByNodoDireccionYSubDireccion(BigDecimal idNodo, BigDecimal idDireccion, BigDecimal idSubDireccion) 
            throws ApplicationException {
        return ( hhppMglManager.findHhppByNodoDireccionYSubDireccion(idNodo, idDireccion, idSubDireccion) );
    }
    
    /**
     * Realiza la b&uacute;squeda de HHPP activos a trav&eacute;s de una cuenta
     * matriz
     *
     * @param cuentaMatriz Identificador del Nodo.
     * @return Listado de HHPP coincidente con la cuenta matriz.
     * @throws ApplicationException
     */
    @Override
    public List<HhppMgl> obtenerHhppCM(CmtCuentaMatrizMgl cuentaMatriz) throws ApplicationException {

        return hhppMglManager.obtenerHhppCM(cuentaMatriz);
    }
    
    /**
     * Realiza la b&uacute;squeda de HHPP activos unica direccion y subdireccion
     * no repetidos por varias tecnologias .
     *
     * @param cuentaMatriz
     * @return Listado de HHPP
     * @throws ApplicationException
     */
    @Override
    public List<HhppMgl> obtenerHhppCMUnicaDirAndSub(CmtCuentaMatrizMgl cuentaMatriz) throws ApplicationException {

        return hhppMglManager.obtenerHhppCMUnicaDirAndSub(cuentaMatriz);
    }
    
    /**
     * Realiza la b&uacute;squeda de HHPP activos unica direccion y subdireccion
     * no repetidos por varias tecnologias por id subedificio .
     *
     * @param HhppSubEdificioId
     * @return Listado de HHPP
     * @throws ApplicationException
     */
    @Override
    public List<HhppMgl> findHhppBySubEdificioIdUnicaDirAndSub(BigDecimal HhppSubEdificioId) {

        return hhppMglManager.findHhppBySubEdificioIdUnicaDirAndSub(HhppSubEdificioId);
    }
    
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
    @Override
    public List<HhppMgl> findHhppByNodoDireccionAndSubDireccion
        (BigDecimal idNodo, BigDecimal idDireccion, BigDecimal idSubDireccion) throws ApplicationException {
        return hhppMglManager.findHhppByNodoDireccionAndSubDireccion(idNodo, idDireccion, idSubDireccion);
    }

    @Override
    public List<HhppMgl> findHhppByDirIdSubDirId(BigDecimal idDireccion, BigDecimal idSubDireccion) throws ApplicationException {
      return hhppMglManager.findHhppByDirIdSubDirId(idDireccion, idSubDireccion);
    }
}
