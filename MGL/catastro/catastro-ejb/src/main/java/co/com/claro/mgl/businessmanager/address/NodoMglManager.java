package co.com.claro.mgl.businessmanager.address;

import co.com.claro.cmas400.ejb.request.MantenimientoBasicoRRNodosRequest;
import co.com.claro.cmas400.ejb.request.RequestDataManttoInfoNodo;
import co.com.claro.cmas400.ejb.respons.MantenimientoBasicoRRBaseResponse;
import co.com.claro.cmas400.ejb.respons.MantenimientoBasicoRRNodosResponse;
import co.com.claro.cmas400.ejb.respons.ResponseManttoInfoNodoList;
import co.com.claro.mer.utils.enums.ParametrosMerEnum;
import co.com.claro.mgl.businessmanager.address.rr.NodoRRManager;
import co.com.claro.mgl.dao.impl.NodoMglDaoImpl;
import co.com.claro.mgl.dao.impl.rr.NodoRRDaoImpl;
import co.com.claro.mgl.dtos.CmtFiltroConsultaNodosDto;
import co.com.claro.mgl.dtos.CmtNodoValidado;
import co.com.claro.mgl.dtos.NodoEstadoDTO;
import co.com.claro.mgl.ejb.wsclient.rest.cm.EnumeratorServiceName;
import co.com.claro.mgl.ejb.wsclient.rest.cm.RestClientBasicMaintenanceRRNodes;
import co.com.claro.mgl.ejb.wsclient.rest.cm.RestClientTablasBasicas;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.PaginacionDto;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtComunidadRr;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.rest.dtos.NodoMerDto;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import com.sun.jersey.api.client.UniformInterfaceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
*
* @author User
*/
public class NodoMglManager {

    ParametrosMglManager parametrosMglManager;
    RestClientBasicMaintenanceRRNodes restClientBasicMaintenanceRRNodes;
    String BASE_URI;
    String BASE_URI_REST;
    RestClientTablasBasicas restClientTablasBasicas;

    public NodoMglManager() {
       
    }
    
    private static final Logger LOGGER = LogManager.getLogger(NodoMglManager.class);
    private final NodoMglDaoImpl nodoMglDaoImpl = new NodoMglDaoImpl();

    public List<GeograficoPoliticoMgl> getCitiesNodoByDivArea(BigDecimal divId, BigDecimal areId) throws ApplicationException {
        List<BigDecimal> resultList;
        resultList = nodoMglDaoImpl.findCodCityByDivArea(divId, areId);
        List<GeograficoPoliticoMgl> listCities = new ArrayList<>();
        GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
        //TODO: implementar query para que reciva List 
        for (BigDecimal idGeo : resultList) {
            GeograficoPoliticoMgl geo = geograficoPoliticoManager.findById(idGeo);
            listCities.add(geo);
        }
        return listCities;
    }

    public List<NodoMgl> findAll() throws ApplicationException {
       List<NodoMgl> result;
        NodoMglDaoImpl nodoMglDaoImpl1 = new NodoMglDaoImpl();
        result = nodoMglDaoImpl1.findAll();
        return result;
    }

    public NodoMgl update(NodoMgl nodoMgl) throws ApplicationException {
        
        try {
            parametrosMglManager = new ParametrosMglManager();
            boolean habilitarRR;
            ParametrosMgl parametroHabilitarRR = parametrosMglManager.findParametroMgl(Constant.HABILITAR_RR);
            if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase("1")) {
                habilitarRR = true;
            }         
            //SE DEJA QUE NO VIAJE A RR POR PETICION DE PRODUCT OWNER VILLAMIL
            habilitarRR = false;  

           if(habilitarRR){
               BASE_URI = parametrosMglManager.findByAcronimo(
                    ParametrosMerEnum.BASE_URI_RESTFULL_BASICA.getAcronimo())
                    .iterator().next().getParValor();
            
               restClientBasicMaintenanceRRNodes
                       = new RestClientBasicMaintenanceRRNodes(BASE_URI);

               BASE_URI_REST = parametrosMglManager.findByAcronimo(
                       co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                       .iterator().next().getParValor();
               restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI_REST);
               if (updateNodoRR(nodoMgl)) {
                   //Autor: Victor Bocanegra
                   //Modificacion de la activacion del nodo en RR

                    RequestDataManttoInfoNodo modActivaNodo = new RequestDataManttoInfoNodo();

                    modActivaNodo.setNombreUsuario(nodoMgl.getNodUsuarioCreacion());
                    modActivaNodo.setCodigo(nodoMgl.getNodCodigo());
                    modActivaNodo.setNombre(nodoMgl.getNodNombre());
                    SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
                    String fechaActivacion = f.format(nodoMgl.getNodFechaActivacion());
                    modActivaNodo.setFechaApertura(fechaActivacion);
                    modActivaNodo.setCostoRed(nodoMgl.getCostoRed().toString());
                    modActivaNodo.setLimites(nodoMgl.getLimites());

                    ResponseManttoInfoNodoList responseManttoInfoNodoList = restClientTablasBasicas.callWebService(
                            EnumeratorServiceName.TB_MANTTO_INFORMACION_NODO_UPDATE,
                            ResponseManttoInfoNodoList.class,
                            modActivaNodo);
                    if (responseManttoInfoNodoList.getResultado()
                            .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                        throw new ApplicationException("(As400)" + responseManttoInfoNodoList.getMensaje());
                    }
                    return nodoMglDaoImpl.update(nodoMgl);

                }
            } else {
                return nodoMglDaoImpl.update(nodoMgl);
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return null;
    }

    public NodoMgl create(NodoMgl nodoMgl) throws ApplicationException {
        LOGGER.error("Creando Nodo:" + nodoMgl.getNodCodigo()
                + " ciudad " + nodoMgl.getGpoId() + " Comunidad " + nodoMgl.getComId().getCodigoRr());
        try {
            parametrosMglManager = new ParametrosMglManager();            
             boolean habilitarRR;           
            ParametrosMgl parametroHabilitarRR = parametrosMglManager.findParametroMgl(Constant.HABILITAR_RR);
            if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase("1")) {
                habilitarRR = true;
            }
            
            //SE DEJA QUE NO VIAJE A RR POR PETICION DE PRODUCT OWNER VILLAMIL
            habilitarRR = false;  
          
            if (habilitarRR) {
                BASE_URI = parametrosMglManager.findByAcronimo(
                    ParametrosMerEnum.BASE_URI_RESTFULL_BASICA.getAcronimo())
                    .iterator().next().getParValor();
            restClientBasicMaintenanceRRNodes
                    = new RestClientBasicMaintenanceRRNodes(BASE_URI);
            
              BASE_URI_REST = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI_REST);
                if (createNodoRR(nodoMgl)) {
                    //Autor: Victor Bocanegra
                    //Creacion de la activacion del nodo en RR
                    RequestDataManttoInfoNodo crearActivaNodo = new RequestDataManttoInfoNodo();

                    crearActivaNodo.setNombreUsuario(nodoMgl.getNodUsuarioCreacion());
                    crearActivaNodo.setCodigo(nodoMgl.getNodCodigo());
                    SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
                    String fechaActivacion = f.format(nodoMgl.getNodFechaActivacion());
                    crearActivaNodo.setFechaApertura(fechaActivacion);
                    crearActivaNodo.setCostoRed(nodoMgl.getCostoRed().toString());
                    crearActivaNodo.setLimites(nodoMgl.getLimites());

                    ResponseManttoInfoNodoList responseManttoInfoNodoList = restClientTablasBasicas.callWebService(
                            EnumeratorServiceName.TB_MANTTO_INFORMACION_NODO_ADD,
                            ResponseManttoInfoNodoList.class,
                            crearActivaNodo);
                    if (responseManttoInfoNodoList.getResultado()
                            .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                        throw new ApplicationException("(As400)" + responseManttoInfoNodoList.getMensaje());
                    }
                    return crearEnMgl(nodoMgl);
                }
            } else {
                   RequestDataManttoInfoNodo crearActivaNodo = new RequestDataManttoInfoNodo();

                    crearActivaNodo.setNombreUsuario(nodoMgl.getNodUsuarioCreacion());
                    crearActivaNodo.setCodigo(nodoMgl.getNodCodigo());
                    SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
                    String fechaActivacion = f.format(nodoMgl.getNodFechaActivacion());
                    crearActivaNodo.setFechaApertura(fechaActivacion);
                    crearActivaNodo.setCostoRed(nodoMgl.getCostoRed().toString());
                    crearActivaNodo.setLimites(nodoMgl.getLimites());
                return crearEnMgl(nodoMgl);
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }

        return null;
    }
    /**
     *
     * @param nodoMgl
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public NodoMgl crearEnMgl(NodoMgl nodoMgl) throws ApplicationException{
            try {
              return  nodoMglDaoImpl.create(nodoMgl);                
            } catch (ApplicationException e) {
                LOGGER.error("Error validando el nodo antes de la creacio en repositorio".concat(e.getMessage()));
            }
            return null;
        }   
        
    public List<NodoMgl> getListNodoByCodigo(String codigo) throws ApplicationException {
        return nodoMglDaoImpl.getListNodoByCodigo(codigo);
    }

    public boolean delete(NodoMgl nodoMgl) throws ApplicationException {

        try {
            parametrosMglManager = new ParametrosMglManager();
            boolean habilitarRR;
            ParametrosMgl parametroHabilitarRR = parametrosMglManager.findParametroMgl(Constant.HABILITAR_RR);
            if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase("1")) {
                habilitarRR = true;
            }
            //SE DEJA QUE NO VIAJE A RR POR PETICION DE PRODUCT OWNER VILLAMIL
            habilitarRR = false;
            
            if (habilitarRR) {
                BASE_URI = parametrosMglManager.findByAcronimo(
                        ParametrosMerEnum.BASE_URI_RESTFULL_BASICA.getAcronimo())
                        .iterator().next().getParValor();
                restClientBasicMaintenanceRRNodes
                        = new RestClientBasicMaintenanceRRNodes(BASE_URI);

                BASE_URI_REST = parametrosMglManager.findByAcronimo(
                        co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                        .iterator().next().getParValor();
                restClientTablasBasicas = new RestClientTablasBasicas(BASE_URI_REST);

                if (deleteNodoRR(nodoMgl)) {
                    RequestDataManttoInfoNodo borrarActivaNodo = new RequestDataManttoInfoNodo();

                    borrarActivaNodo.setNombreUsuario(nodoMgl.getNodUsuarioCreacion());
                    borrarActivaNodo.setCodigo(nodoMgl.getNodCodigo());
                    SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
                    String fechaActivacion = f.format(nodoMgl.getNodFechaActivacion());
                    borrarActivaNodo.setFechaApertura(fechaActivacion);
                    borrarActivaNodo.setCostoRed(nodoMgl.getCostoRed().toString());
                    borrarActivaNodo.setLimites(nodoMgl.getLimites());

                    ResponseManttoInfoNodoList responseManttoInfoNodoList = restClientTablasBasicas.callWebService(
                            EnumeratorServiceName.TB_MANTTO_INFORMACION_NODO_DELETE,
                            ResponseManttoInfoNodoList.class,
                            borrarActivaNodo);
                    if (responseManttoInfoNodoList.getResultado()
                            .equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                        throw new ApplicationException("(As400)" + responseManttoInfoNodoList.getMensaje());
                    }
                    nodoMgl.setEstadoRegistro(0);
                    nodoMglDaoImpl.update(nodoMgl);
                    return true;
                }
            } else {
                nodoMgl.setEstadoRegistro(0);
                nodoMglDaoImpl.update(nodoMgl);
                return true;
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return false;
    }

    public NodoMgl findById(BigDecimal id) throws ApplicationException {
        return nodoMglDaoImpl.find(id);
    }

    public NodoMgl findByName(String name) {
        return nodoMglDaoImpl.findByName(name);
    }

    public boolean vetoNodoByCitiesDivArea(
            List<GeograficoPoliticoMgl> cities,
            BigDecimal divisional,
            BigDecimal area,
            Date iniVeto,
            Date finVeto,
            String politica,
            String correo) throws ApplicationException {
        List<NodoMgl> nodosVetarList = findNodosByCitytipos(cities, divisional, area);
        VetoNodoManager vetoNodoManager = new VetoNodoManager();
        return vetoNodoManager.vetarNodos(nodosVetarList, iniVeto, finVeto, politica, correo);
    }

    public List<NodoMgl> findNodosByCitytipos(List<GeograficoPoliticoMgl> cities, BigDecimal divisional, BigDecimal area) throws ApplicationException {
        return nodoMglDaoImpl.findNodosByCitytipos(cities, divisional, area);
    }

    public List<NodoMgl> findNodosByComDivArea(List<BigDecimal> comunidad, BigDecimal divisional, BigDecimal area, List<String> tipos) throws ApplicationException {
        return nodoMglDaoImpl.findNodosByComDivArea(comunidad, divisional, area, tipos);
    }

    public boolean vetoNodosByCityDivAre(List<NodoMgl> nodoList,
            BigDecimal divisional, BigDecimal area,
            Date iniVeto, Date finVeto,
            String politica, String correo) throws ApplicationException {

        VetoNodoManager vetoNodoManager = new VetoNodoManager();
        return vetoNodoManager.vetarNodos(nodoList, iniVeto, finVeto, politica, correo);
    }

    public boolean isNodoCertificado(String nodo) throws ApplicationException {
        boolean isCertificado;
        CmtBasicaMgl estado = null;
        String niac = Constant.BASICA_TIPO_ESTADO_NODO_NO_CERTIFICADO;
        try {
            estado = nodoMglDaoImpl.findByCodigo(nodo).getEstado();
        } catch (ApplicationException e) {
            LOGGER.error("Error en vetoNodosByCityDivAre. " +e.getMessage(), e);  
        }
        isCertificado = estado != null && !estado.getIdentificadorInternoApp().equals(niac)
                && !estado.getIdentificadorInternoApp().isEmpty();
        return isCertificado;
    }

    public List<NodoMgl> findByLikeCodigo(String codigo) throws ApplicationException {
        return nodoMglDaoImpl.findByLikeCodigo(codigo);
    }

    public NodoMgl findByCodigo(String codigo) throws ApplicationException {
        return nodoMglDaoImpl.findByCodigo(codigo);
    }

    public NodoMgl findByCodigoAndComunidad(
            String codigo,
            String comunidad) throws ApplicationException {
        GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
        GeograficoPoliticoMgl city = geograficoPoliticoManager.findCityByCodComunidad(comunidad);
        return findByCodigoAndCity(codigo, city.getGpoId());
    }

    public NodoMgl findByCodigoAndCity(String codigo, BigDecimal gpoId) throws ApplicationException {
        return nodoMglDaoImpl.findByCodigoAndCity(codigo, gpoId);
    }

    public List<NodoMgl> findNodosByCity(BigDecimal gpoId) throws ApplicationException {
        return nodoMglDaoImpl.findNodosByCity(gpoId);
    }

    public CmtNodoValidado validarNodo(String nodoValidar, String comunidad, String userVt) throws ApplicationException {

        CmtNodoValidado cmtNodoValidado = new CmtNodoValidado();
        NodoRRManager managerRRnode = new NodoRRManager();

        if (nodoValidar.contains(":")) {
            nodoValidar = nodoValidar.split(":")[1];
        }
        NodoRRDaoImpl nodoRRDaoImpl = new NodoRRDaoImpl();
        co.com.claro.mgl.jpa.entities.rr.NodoRR nodoRr = nodoRRDaoImpl.find(nodoValidar);

        if (nodoValidar != null && !nodoValidar.isEmpty()) {

            Boolean responceRr = managerRRnode.isNodoRRCertificado(nodoValidar, userVt); 
            if (responceRr) {
                cmtNodoValidado.setMessage(cmtNodoValidado.getMessage() + " Nodo " + nodoValidar + " certificado. \n");

            } else {
                cmtNodoValidado.setMessage(cmtNodoValidado.getMessage() + " Nodo " + nodoValidar + " no certificado. \n");

            }

            if (nodoRr == null) {
                throw new ApplicationException("El nodo " + nodoValidar + " no existe en RR o repositorio, consultar con el Area de HHPP");
            }

            NodoMgl nodoMgl = null;
            try {
                nodoMgl = findByCodigo(nodoValidar);
            } catch (ApplicationException ex) {
                String msg = "Error al actualizar la solictud, validando la existencia del nodo " + nodoValidar + ":..." + ex.getMessage();
                LOGGER.error(msg);
                throw new ApplicationException(msg);
            }
            if (nodoMgl == null) {
                String msg = "El nodo  " + nodoValidar + " no existe en el repositorio o en RR, consultar con el Area de HHPP";
                throw new ApplicationException(msg);
            }
            cmtNodoValidado.setNodoMgl(nodoMgl);
            return cmtNodoValidado;
        } else {
            String msg = "No hay valor nodo para validar";
            throw new ApplicationException(msg);
        }
    }

    /**
     * * valbuenayf Metodo para buscar el nodo por el codigo del nodo, id de la
     * ciudad y el id de la tecnologia
     *
     * @param codigo
     * @param idCentroPoblado
     * @param idTecnologia
     * @return
     * @throws ApplicationException
     */
    public NodoMgl findByCodigoNodo(String codigo, BigDecimal idCentroPoblado, BigDecimal idTecnologia) throws ApplicationException {
        return nodoMglDaoImpl.findByCodigoNodo(codigo, idCentroPoblado, idTecnologia);
    }

    /**
     * *Victor Bocanegra Metodo para buscar los nodos paginados en la tabla
     *
     * @param paginaSelected
     * @param maxResults
     * @param consulta
     * @return PaginacionDto<NodoMgl>
     * @throws ApplicationException
     */
    public PaginacionDto<NodoMgl> findAllPaginado(int paginaSelected,
            int maxResults, CmtFiltroConsultaNodosDto consulta, GeograficoPoliticoManager geograficoPoliticoManager,
            boolean soloConteoNodos) throws ApplicationException {
            PaginacionDto<NodoMgl> resultado = new PaginacionDto<>();
            int firstResult = 0;
            if (paginaSelected > 0) {
                firstResult = (maxResults * (paginaSelected - 1));
            }

            if (consulta != null) {
                resultado.setNumPaginas(nodoMglDaoImpl.countByNodFiltro(consulta));
                resultado.setListResultado(nodoMglDaoImpl.findByFiltro(firstResult, maxResults, consulta));
            } else {
                resultado.setNumPaginas(nodoMglDaoImpl.countAllItems());
                List<NodoMgl> nodoList = nodoMglDaoImpl.findItemsPaginacion(firstResult, maxResults);
                if (nodoList != null && !nodoList.isEmpty()) {
                    List<NodoMerDto> nodoMerDtoList = new ArrayList();

                    for (NodoMgl nodo : nodoList) {

                        NodoMerDto nodoMer = new NodoMerDto();
                        nodoMer.setCodigo(nodo.getNodCodigo() != null ? nodo.getNodCodigo() : "");
                        nodoMer.setNombre(nodo.getNodNombre() != null ? nodo.getNodNombre() : "");
                        nodoMer.setComunidad(nodo.getComId() != null ? nodo.getComId().getNombreComunidad() : "");
                        nodoMer.setDivision((nodo.getComId() != null && nodo.getComId().getRegionalRr() != null) ? nodo.getComId().getRegionalRr().getNombreRegional() : "");
                        nodoMer.setArea(nodo.getAreId() != null ? nodo.getAreId().getNombreBasica() : "");
                        nodoMer.setDistrito(nodo.getDisId() != null ? nodo.getDisId().getNombreBasica() : "");
                        nodoMer.setZona(nodo.getZonId() != null ? nodo.getZonId().getNombreBasica() : "");
                        nodoMer.setUnidadGestion(nodo.getUgeId() != null ? nodo.getUgeId().getNombreBasica() : "");
                        nodoMer.setEstadoNodo(nodo.getEstado() != null ? nodo.getEstado().getNombreBasica() : "");
                        nodoMer.setUsuarioCreacion(nodo.getNodUsuarioCreacion() != null ? nodo.getNodUsuarioCreacion() : "");
                        nodoMer.setTipoNodo(nodo.getNodTipo() != null && nodo.getNodTipo().getNombreBasica() != null ? nodo.getNodTipo().getNombreBasica() : "");
                        nodoMer.setOpera (nodo.getOpera() != null ? nodo.getOpera() : "");
                        nodoMer.setOlt (nodo.getOlt() != null ? nodo.getOlt() : "");
                        nodoMer.setOltNodo (nodo.getOltNodo() != null ? nodo.getOltNodo() : "");
                        nodoMer.setOt (nodo.getOt() != null ? nodo.getOt() : "");

                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        if (nodo.getNodFechaCreacion() != null) {
                            nodoMer.setFechaCreacion(sdf.format(nodo.getNodFechaCreacion()));
                        } else {
                            nodoMer.setFechaCreacion("");
                        }
                        if (nodo.getNodFechaModificacion() != null) {
                            nodoMer.setFechaModificacion(sdf.format(nodo.getNodFechaModificacion()));
                        } else {
                            nodoMer.setFechaModificacion("");
                        }

                        if (nodo.getNodFechaActivacion() != null) {
                            nodoMer.setFechaApertura(sdf.format(nodo.getNodFechaActivacion()));
                        } else {
                            nodoMer.setFechaApertura("");
                        }

                        if (nodo.getGpoId() != null) {
                            GeograficoPoliticoMgl geoCentro = geograficoPoliticoManager.
                                    findById(nodo.getGpoId());
                            if (geoCentro.getGpoId() != null) {
                                //centroPoblado
                                nodoMer.setCentroPoblado(geoCentro.getGpoNombre() != null ? geoCentro.getGpoNombre() : "");

                                GeograficoPoliticoMgl geoCiudad = geograficoPoliticoManager.
                                        findById(geoCentro.getGeoGpoId());
                                if (geoCiudad.getGpoId() != null) {
                                    //Ciudad
                                    nodoMer.setCiudad(geoCiudad.getGpoNombre() != null ? geoCiudad.getGpoNombre() : "");

                                    GeograficoPoliticoMgl geoDpto = geograficoPoliticoManager.
                                            findById(geoCiudad.getGeoGpoId());
                                    if (geoDpto.getGpoId() != null) {
                                        nodoMer.setDepartamento(geoDpto.getGpoNombre() != null ? geoDpto.getGpoNombre() : "");
                                    }
                                }
                            }
                        } else {
                            nodoMer.setCentroPoblado("");
                            nodoMer.setCiudad("");
                            nodoMer.setDepartamento("");
                        }

                        //Se añade el objeto dto lleno de datos
                        nodoMerDtoList.add(nodoMer);
                    }
                    resultado.setListResultadoNodoMer(nodoMerDtoList);
                    nodoList.clear();
                    nodoList = null;
                    nodoList = new ArrayList();
                    System.gc();

                } else {
                    resultado.setListResultadoNodoMer(null);
                }

            }


            return resultado;
    }

    /**
     * Función que obtiene listado de nodos por centro poblado y tipo de
     * tecnología
     *
     * @author Juan David Hernandez
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException 
     */
    public List<NodoMgl> findNodosCentroPobladoAndTipoTecnologia(int page,
            int maxResults, BigDecimal gpoId,
            CmtBasicaMgl nodTipo) throws ApplicationException {

        int firstResult = 0;
        if (page > 1) {
            firstResult = (maxResults * (page - 1));
        }
        return nodoMglDaoImpl.findNodosCentroPobladoAndTipoTecnologia(firstResult,
                maxResults, gpoId,
                nodTipo);
    }

    public NodoMgl findByNodoAndCity(String codNodo, BigDecimal ciudad) throws ApplicationException {
        return nodoMglDaoImpl.findByCodigoAndCity(codNodo, ciudad);
    }

    /**
     * Función que obtiene listado de nodos por centro poblado y tipo de
     * tecnología
     *
     * @author Juan David Hernandez
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException 
     */
    public List<NodoMgl> findNodosCiudadAndTipoTecnologia(int page,
            int maxResults, BigDecimal gpoId,
            CmtBasicaMgl nodTipo) throws ApplicationException {

        int firstResult = 0;
        if (page > 1) {
            firstResult = (maxResults * (page - 1));
        }
        return nodoMglDaoImpl.findNodosCiudadAndTipoTecnologia(firstResult,
                maxResults, gpoId, nodTipo);
    }

    /**
     * Función que obtiene la cantidad total de nodos por centro poblado y de
     * resultados.
     *
     * @author Juan David Hernandez
     * @param gpoId
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException 
     */
    public int countNodosCentroPobladoAndTipoTecnologia(BigDecimal gpoId, CmtBasicaMgl nodTipo) throws ApplicationException {

        return nodoMglDaoImpl.countNodosCentroPobladoAndTipoTecnologia(gpoId,
                nodTipo);
    }

    /**
     * Función que obtiene cantidad de nodos por centro poblado y tipo de
     * tecnología
     *
     * @author Juan David Hernandez
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException 
     */
    public int countNodosCiudadAndTipoTecnologia(BigDecimal gpoId,
            CmtBasicaMgl nodTipo) throws ApplicationException {

        return nodoMglDaoImpl.countNodosCiudadAndTipoTecnologia(gpoId, nodTipo);
    }

    /**
     * Buscar nodos Busca los notos que correspondan a la ciudad y a la
     * tecnología
     *
     * @author becerraarmr
     * @param gpoId id del centro poblad
     * @param nodTipo tipo de tecnología
     * @return listado de nodos encontrados.
     * @throws ApplicationException se hay error en la solicitud
     */
    public List<NodoMgl> findNodosCentroPobladoAndTipoTecnologia(BigDecimal gpoId,
            CmtBasicaMgl nodTipo) throws ApplicationException {
        return nodoMglDaoImpl.findNodosCentroPobladoAndTipoTecnologia(gpoId, nodTipo);
    }

    /**
     * Buscar nodos Busca los notos que correspondan a la ciudad y a la
     * tecnología
     *
     * @author becerraarmr
     * @param gpoId id ciudad
     * @param nodTipo tipo de tecnología
     * @return listado de nodos encontrados.
     * @throws ApplicationException se hay error en la solicitud
     */
    public List<NodoMgl> findNodosCiudadAndTipoTecnologia(BigDecimal gpoId,
            CmtBasicaMgl nodTipo) throws ApplicationException {
        return nodoMglDaoImpl.findNodosCiudadAndTipoTecnologia(gpoId, nodTipo);
    }

    /**
     * Buscar Nodos Busca los nodos que correspondan según los parámetros.
     *
     * @author becerraarmr
     * @param nodTipo tipo de tecnología
     * @param gpoId centro poblado
     * @param codNodo código del nodo
     * @return listado encontrado
     * @throws ApplicationException se hay error en la solitud.
     */
    public List<NodoMgl> findNodos(CmtBasicaMgl nodTipo,
            BigDecimal gpoId, String codNodo) throws ApplicationException {
        if (codNodo == null) {
            return nodoMglDaoImpl.findNodos(nodTipo, gpoId);
        }
        return nodoMglDaoImpl.findNodos(nodTipo, gpoId, codNodo);
    }

    /**
     * *Victor Bocanegra Metodo para validar los nuevos nodos para modificar
     * CM.
     *
     * @param nodoValidar
     * @param geograficoPoliticoMgl
     * @param cmtBasicaMgl
     * @return CmtNodoValidado
     * @throws ApplicationException
     */
    public CmtNodoValidado validarNodoModCM(String nodoValidar,
            GeograficoPoliticoMgl geograficoPoliticoMgl, CmtBasicaMgl cmtBasicaMgl) throws ApplicationException {

        String nombreTecno="";
        if(cmtBasicaMgl != null){
            nombreTecno= cmtBasicaMgl.getNombreBasica();
        }
        CmtNodoValidado cmtNodoValidado = new CmtNodoValidado();
           
        if (nodoValidar.contains(":")) {
            nodoValidar = nodoValidar.split(":")[1];
        }

        if (nodoValidar != null && !nodoValidar.isEmpty()) {

            NodoMgl nodoMgl = null;
            String additionalMessage = "";
            try {
                if (geograficoPoliticoMgl != null && cmtBasicaMgl != null) {
                    nodoMgl = findByCodigoNodo(nodoValidar, geograficoPoliticoMgl.getGpoId(), cmtBasicaMgl.getBasicaId());
                }
                else {
                    String msg = "Error al actualizar la solicitud, validando la existencia del nodo "+nodoValidar+": ";
                    if (geograficoPoliticoMgl == null) {
                        String razon = "Centro Poblado nulo.";
                        msg += razon;
                        additionalMessage += razon;
                        LOGGER.error(msg);
                    }
                    if (cmtBasicaMgl == null) {
                        String razon = "Tecnología nula.";
                        msg += razon;
                        additionalMessage += razon;
                        LOGGER.error(msg);
                    }
                }
            } catch (ApplicationException ex) {
                String msg = "Error al actualizar la solicitud, validando la existencia del nodo " + nodoValidar + ":..." + ex.getMessage();
                LOGGER.error(msg);
                throw new ApplicationException(msg);
            }
            if (nodoMgl == null) {
                String msg = "El nodo  " + nodoValidar + " no existe o no pertenece a la tecnologia:  "+nombreTecno+", consultar con el Area de HHPP";
                if (!additionalMessage.isEmpty()) {
                    msg += ": "+additionalMessage;
                }
                throw new ApplicationException(msg);
            }
            cmtNodoValidado.setNodoMgl(nodoMgl);
            return cmtNodoValidado;
        } else {
            String msg = "No hay valor nodo para validar";
            throw new ApplicationException(msg);
        }
    }

    /**
     * *Victor Bocanegra busqueda del nodo por codigo y comunidaddRR.
     *
     * @param codigo
     * @param cmtComunidadRr
     * @return NodoMgl
     * @throws ApplicationException
     */
    public NodoMgl findByCodigoAndComunidadRR(String codigo, CmtComunidadRr cmtComunidadRr)
            throws ApplicationException {

        return nodoMglDaoImpl.findByCodigoAndComunidadRR(codigo, cmtComunidadRr);

    }

    // crud de RR
    /**
     * Metodo para realizar la insercion a RR por pcml
     *
     * @param nodoRR a registrar
     * @return boolean define si la operacion se realizao correctamente o no
     * @throws ApplicationException
     */
    private boolean createNodoRR(NodoMgl nodoRR) throws ApplicationException {
        try {
            MantenimientoBasicoRRNodosRequest mantenimientoBasicoRRNodosRequest
                    = new MantenimientoBasicoRRNodosRequest();

            mantenimientoBasicoRRNodosRequest.setIDUSER(nodoRR.getNodUsuarioCreacion());
            mantenimientoBasicoRRNodosRequest.setCODNOD("" + nodoRR.getNodCodigo());
            mantenimientoBasicoRRNodosRequest.setCODNOM(nodoRR.getNodNombre());
            mantenimientoBasicoRRNodosRequest.setCODALI(
                    nodoRR.getAliadoId().getCodigoBasica());
            mantenimientoBasicoRRNodosRequest.setCODDIV("" + nodoRR.getComId().getRegionalRr().getCodigoRr());
            mantenimientoBasicoRRNodosRequest.setCODDVS("" + nodoRR.getDivId().getAbreviatura());
            mantenimientoBasicoRRNodosRequest.setCODARE("" + nodoRR.getAreId().getAbreviatura());
            mantenimientoBasicoRRNodosRequest.setCODZON("" + nodoRR.getZonId().getAbreviatura());
            mantenimientoBasicoRRNodosRequest.setCODCOM("" + nodoRR.getComId().getCodigoRr());
            mantenimientoBasicoRRNodosRequest.setCODDIS("" + nodoRR.getDisId().getAbreviatura());
            mantenimientoBasicoRRNodosRequest.setCODUNI("" + nodoRR.getUgeId().getAbreviatura());
            mantenimientoBasicoRRNodosRequest.setTIPRED("" + nodoRR.getNodTipo().
                    getAbreviatura());
            mantenimientoBasicoRRNodosRequest.setEDONOD(nodoRR.getEstado().getCodigoBasica());

            MantenimientoBasicoRRBaseResponse mantenimientoBasicoRRBaseResponse
                    = restClientBasicMaintenanceRRNodes.callWebServiceMethodPost(
                            EnumeratorServiceName.BASICARR_NODE_CREAR,
                            MantenimientoBasicoRRBaseResponse.class,
                            mantenimientoBasicoRRNodosRequest);
            if (!mantenimientoBasicoRRBaseResponse.getCodigoDeRespuesta()
                    .equalsIgnoreCase(Constant.ACCION_EXITOSA_RR)) {
                throw new ApplicationException("(As400)"
                        + mantenimientoBasicoRRBaseResponse.getMensajeDeRespuesta());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    /**
     * Metodo para realizar la actualizacion a RR por pcml
     *
     * @param nodoRR nodo a registrar
     * @return boolean define si la operacion se realizao correctamente o no
     * @throws ApplicationException
     */
    private boolean updateNodoRR(NodoMgl nodoRR) throws ApplicationException {
        try {
            MantenimientoBasicoRRNodosRequest mantenimientoBasicoRRNodosRequest
                    = new MantenimientoBasicoRRNodosRequest();

            mantenimientoBasicoRRNodosRequest.setIDUSER(nodoRR.getNodUsuarioModificacion());
            mantenimientoBasicoRRNodosRequest.setCODNOD("" + nodoRR.getNodCodigo());
            mantenimientoBasicoRRNodosRequest.setCODNOM(nodoRR.getNodNombre());
            mantenimientoBasicoRRNodosRequest.setCODALI(nodoRR.getAliadoId().getCodigoBasica());
            mantenimientoBasicoRRNodosRequest.setCODDIV("" + nodoRR.getComId().getRegionalRr().getCodigoRr());
            mantenimientoBasicoRRNodosRequest.setCODDVS("" + nodoRR.getDivId().getAbreviatura());
            mantenimientoBasicoRRNodosRequest.setCODARE("" + nodoRR.getAreId().getAbreviatura());
            mantenimientoBasicoRRNodosRequest.setCODZON("" + nodoRR.getZonId().getAbreviatura());
            mantenimientoBasicoRRNodosRequest.setCODCOM("" + nodoRR.getComId().getCodigoRr());
            mantenimientoBasicoRRNodosRequest.setCODDIS("" + nodoRR.getDisId().getAbreviatura());
            mantenimientoBasicoRRNodosRequest.setCODUNI("" + nodoRR.getUgeId().getAbreviatura());
            mantenimientoBasicoRRNodosRequest.setTIPRED("" + nodoRR.getNodTipo().getAbreviatura());
            mantenimientoBasicoRRNodosRequest.setEDONOD(nodoRR.getEstado().getCodigoBasica());

            MantenimientoBasicoRRBaseResponse mantenimientoBasicoRRBaseResponse
                    = restClientBasicMaintenanceRRNodes.callWebServicePut(
                            EnumeratorServiceName.BASICARR_NODE_ACTUALIZAR,
                            MantenimientoBasicoRRBaseResponse.class,
                            mantenimientoBasicoRRNodosRequest);
            if (!mantenimientoBasicoRRBaseResponse.getCodigoDeRespuesta()
                    .equalsIgnoreCase(Constant.ACCION_EXITOSA_RR)) {
                throw new ApplicationException("(As400)"
                        + mantenimientoBasicoRRBaseResponse.getMensajeDeRespuesta());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage(), ex);
        }
        return true;
    }

    /**
     * Metodo para realizar la eliminaciion de un nodo a RR por pcml
     *
     * @param nodoRR nodo a registrar
     * @return boolean define si la operacion se realizao correctamente o no
     * @throws ApplicationException
     */
    private boolean deleteNodoRR(NodoMgl nodoRR) throws ApplicationException {
        try {
            MantenimientoBasicoRRNodosRequest mantenimientoBasicoRRNodosRequest
                    = new MantenimientoBasicoRRNodosRequest();

            mantenimientoBasicoRRNodosRequest.setIDUSER("" );
            mantenimientoBasicoRRNodosRequest.setCODNOD("" + nodoRR.getNodCodigo());
            mantenimientoBasicoRRNodosRequest.setCODNOM("" );
            mantenimientoBasicoRRNodosRequest.setCODALI("" );
            mantenimientoBasicoRRNodosRequest.setCODDIV("" );
            mantenimientoBasicoRRNodosRequest.setCODDVS("" );
            mantenimientoBasicoRRNodosRequest.setCODARE("" );
            mantenimientoBasicoRRNodosRequest.setCODZON("" );
            mantenimientoBasicoRRNodosRequest.setCODCOM("" );
            mantenimientoBasicoRRNodosRequest.setCODDIS("" );
            mantenimientoBasicoRRNodosRequest.setCODUNI("" );
            mantenimientoBasicoRRNodosRequest.setTIPRED("" );
            mantenimientoBasicoRRNodosRequest.setEDONOD("" );

            MantenimientoBasicoRRBaseResponse mantenimientoBasicoRRBaseResponse
                    = restClientBasicMaintenanceRRNodes.callWebServicePut(
                            EnumeratorServiceName.BASICARR_NODE_ELIMINAR,
                            MantenimientoBasicoRRBaseResponse.class,
                            mantenimientoBasicoRRNodosRequest);
            if (!mantenimientoBasicoRRBaseResponse.getCodigoDeRespuesta()
                    .equalsIgnoreCase(Constant.ACCION_EXITOSA_RR)) {
                throw new ApplicationException("(As400)"
                        + mantenimientoBasicoRRBaseResponse.getMensajeDeRespuesta());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }

    /**
     * Metodo para realizar la consulta de un nodo a RR por pcml
     *
     * @param nodoRR nodo a registrar
     * @return boolean define si la operacion se realizao correctamente o no
     * @throws ApplicationException
     */
    private boolean findNodoRR(NodoMgl nodoRR) throws ApplicationException {
        try {
            MantenimientoBasicoRRNodosRequest mantenimientoBasicoRRNodosRequest
                    = new MantenimientoBasicoRRNodosRequest();

            mantenimientoBasicoRRNodosRequest.setIDUSER(nodoRR.getNodUsuarioCreacion());
            mantenimientoBasicoRRNodosRequest.setCODNOD("" + nodoRR.getNodCodigo());
            mantenimientoBasicoRRNodosRequest.setCODNOM("" + nodoRR.getNodNombre());
            mantenimientoBasicoRRNodosRequest.setCODALI(""
                    + nodoRR.getAliadoId().getCodigoBasica());
            mantenimientoBasicoRRNodosRequest.setCODDIV("" + nodoRR.getComId().getRegionalRr().getCodigoRr());
            mantenimientoBasicoRRNodosRequest.setCODDVS("" + nodoRR.getDivId().getCodigoBasica());
            mantenimientoBasicoRRNodosRequest.setCODARE("" + nodoRR.getAreId().getCodigoBasica());
            mantenimientoBasicoRRNodosRequest.setCODZON("" + nodoRR.getZonId().getCodigoBasica());
            mantenimientoBasicoRRNodosRequest.setCODCOM("" + nodoRR.getComId().getCodigoRr());
            mantenimientoBasicoRRNodosRequest.setCODDIS("" + nodoRR.getDisId().getCodigoBasica());
            mantenimientoBasicoRRNodosRequest.setCODUNI("" + nodoRR.getUgeId().getCodigoBasica());
            mantenimientoBasicoRRNodosRequest.setTIPRED("" + nodoRR.getNodTipo().
                    getAbreviatura());

            if (!nodoRR.getEstado().getIdentificadorInternoApp().
                    equalsIgnoreCase(Constant.BASICA_TIPO_ESTADO_NODO_CERTIFICADO)) {

                mantenimientoBasicoRRNodosRequest.setEDONOD(Constant.BASICA_TIPO_ESTADO_NODO_NO_CERTIFICADO);

            } else {
                mantenimientoBasicoRRNodosRequest.setEDONOD(Constant.BASICA_TIPO_ESTADO_NODO_CERTIFICADO);

            }

            MantenimientoBasicoRRNodosResponse mantenimientoBasicoRRNodosResponse
                    = restClientBasicMaintenanceRRNodes.callWebServicePut(
                            EnumeratorServiceName.BASICARR_NODE_OBTENER,
                            MantenimientoBasicoRRNodosResponse.class,
                            mantenimientoBasicoRRNodosRequest);
            if (!mantenimientoBasicoRRNodosResponse.getENDMSG()
                    .equalsIgnoreCase(Constant.ACCION_EXITOSA_RR)) {
                throw new ApplicationException("(As400)"
                        + mantenimientoBasicoRRNodosResponse.getENDMSG());
            }
        } catch (ApplicationException | UniformInterfaceException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg); 
            throw new ApplicationException("(As400-ws)" + ex.getMessage());
        }
        return true;
    }
    
    /**
     * *Victor Bocanegra Metodo para buscar los nodos por los filtros
     *
     * @param consulta
     * @return List<NodoMgl>
     * @throws ApplicationException
     */
    public List<NodoMgl> findByFiltroExportar(CmtFiltroConsultaNodosDto consulta)
            throws ApplicationException {
        
        return nodoMglDaoImpl.findByFiltroExportar(consulta);
    }
    
    
    /**
     * Victor Bocanegra Metodo para consultar un nodo por el geo
     *
     * @param ot
     * @return NodoMgl
     * @throws ApplicationException
     */
    public NodoMgl consultaGeo(CmtOrdenTrabajoMgl ot) throws ApplicationException {

        return nodoMglDaoImpl.consultaGeo(ot);
    }
    
    /**
     * Victor Bocanegra Metodo para consultar lista de codigos de nodo por
     * centro poblado
     *
     * @param centroPoblado
     * @return List<String>
     * @throws ApplicationException
     */
    public List<String> findCodigoNodoByCentroP(BigDecimal centroPoblado)
            throws ApplicationException {
        
        return nodoMglDaoImpl.findCodigoNodoByCentroP(centroPoblado);
    }
    
    
      /**
     * Buscar nodos Busca los notos que correspondan a la ciudad y a la
     * tecnología
     *
     * @author @cardenaslb
     * @param gpoId id ciudad
     * @param nodTipo tipo de tecnología
     * @param estado estado
     * @return listado de nodos encontrados.
     * @throws ApplicationException se hay error en la solicitud
     */
    public List<NodoMgl> findNodosCiudadAndTecnoAndEstado(BigDecimal gpoId,
            CmtBasicaMgl nodTipo, CmtBasicaMgl estado) throws ApplicationException {
        return nodoMglDaoImpl.findNodosCiudadAndTecnoAndEstado(gpoId, nodTipo,estado);
    }
    
    
   /**
     * Busca un nodo NFI por comunidad
     *
     * @author Victor Bocanegra
     * @param comunidad del nodo
     * @param codigoNFI 
     * @return  nodo NFI encontrado.
     * @throws ApplicationException se hay error en la solicitud
     */
    public NodoMgl findNodoNFIByComunidad(CmtComunidadRr comunidad, String codigoNFI) 
            throws ApplicationException {
        return nodoMglDaoImpl.findNodoNFIByComunidad(comunidad, codigoNFI);
}
    
      /**
     * Lista de codigos de nodos por centro pobaldo
     *
     * @author cardenaslb
     * @param centroPoblado 
     * @return  List<NodoMgl>
     * @throws ApplicationException se hay error en la solicitud
     */
    public List<NodoMgl> findCodigosByCentroPoblado(BigDecimal centroPoblado)
            throws ApplicationException {
        return nodoMglDaoImpl.findCodigosByCentroPoblado(centroPoblado);
    }
    
    
      /**
     * Juan David Hernandez Método para consultar solo 5 nodos del centro poblado
     *
     * @param centroPoblado
     * @return List<NodoMgl>
     * @throws ApplicationException
     */
    public List<NodoMgl> find5NodosByCentroPobladoList(BigDecimal centroPoblado)
            throws ApplicationException {
        
        return nodoMglDaoImpl.find5NodosByCentroPobladoList(centroPoblado);
    }
    
    	    /**
     * *Bocanegra Vm Metodo para buscar el nodo por el codigo del nodo, codigo
     * dane de la ciudad y el id de la tecnologia
     *
     * @param codigo
     * @param codigodane
     * @param idTecnologia
     * @return
     * @throws ApplicationException
     */
    public NodoMgl findByCodigoNodoDaneAndTec(String codigo, String codigodane,
            BigDecimal idTecnologia) throws ApplicationException {

        return nodoMglDaoImpl.findByCodigoNodoDaneAndTec(codigo, codigodane, idTecnologia);
    }

    public List<NodoMerDto> findAllPaginadoExport(int minResults, int maxResults, CmtFiltroConsultaNodosDto consulta) throws ApplicationException {

        List<Object[]> listaNodos;
        List<NodoMerDto> listaNodosR = new ArrayList<>();

        try {
            listaNodos = nodoMglDaoImpl.findByFiltroMun(minResults, maxResults, consulta);

            for (Object[] nodo : listaNodos) {

                    NodoMerDto nodoMer = NodoMerDto.builder()
                            .codigo(nodo[11] != null ? nodo[11].toString() : "")
                            .nombre(nodo[16] != null ? nodo[16].toString() : "")
                            .comunidad(nodo[34] != null ? nodo[34].toString() : "")
                            .division(nodo[31] != null ? nodo[31].toString() : "")
                            .area(nodo[23] != null ? nodo[23].toString() : "")
                            .distrito(nodo[25] != null ? nodo[25].toString() : "")
                            .zona(nodo[30] != null ? nodo[30].toString() : "")
                            .unidadGestion(nodo[29] != null ? nodo[29].toString() : "")
                            .estadoNodo(nodo[27] != null ? nodo[27].toString() : "")
                            .usuarioCreacion(nodo[17] != null ? nodo[17].toString() : "")
                            .tipoNodo(nodo[28] != null ? nodo[28].toString() : "")
                            .fechaCreacion(nodo[13] != null ? stringToDate(nodo[13].toString()) : "")
                            .fechaModificacion(nodo[14] != null ? stringToDate(nodo[14].toString()) : "")
                            .fechaApertura(nodo[12] != null ? stringToDate(nodo[12].toString()) : "")
                            .centroPoblado(nodo[31] != null ? nodo[31].toString() : "")
                            .ciudad(nodo[32] != null ? nodo[32].toString() : "")
                            .departamento(nodo[33] != null ? nodo[33].toString() : "")
                            .build();

                listaNodosR.add(nodoMer);
            }
        }catch (Exception e){
            LOGGER.error(e);
            throw new ApplicationException("Ocurrió un error realizando consulta de nodos, por favor intente de nuevo.");
        }
        return listaNodosR;
    }

    public static String stringToDate(String dateIn){
        Calendar cal = Calendar.getInstance();;
        try{
            cal.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateIn));
        }catch (ParseException e){
            LOGGER.error(e);
            return "";
        }
        return new SimpleDateFormat("dd/MM/yyyy").format(cal.getTime());
    }

    /**
     *
     * @param consulta
     * @return
     * @throws ApplicationException
     */
    public Integer countAllPaginadoExport(CmtFiltroConsultaNodosDto consulta) throws ApplicationException {
        try {
            return nodoMglDaoImpl.countByFiltro(consulta);
        }catch (Exception e){
            LOGGER.error(e);
            throw new ApplicationException("Ocurrió un error realizando consulta de nodos, por favor intente de nuevo.");
        }
    }

    /**
     * Metodo para consultar los estados existentes de los nodos
     * @return
     * @throws ApplicationException
     */
    public List<NodoEstadoDTO> consultarEstadoNodos() throws ApplicationException {
        try {
            return nodoMglDaoImpl.consultarEstadoNodos();
        }catch (Exception e){
            LOGGER.error(e);
            throw new ApplicationException("Ocurrió un error realizando consulta de estados de nodos");
        }

    }
}
