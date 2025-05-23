package co.com.telmex.catastro.services.solicitud;

import co.com.claro.direcciones.business.NegocioLocalidad;
import co.com.claro.mgl.businessmanager.cm.CmtBasicaMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtTipoBasicaMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.visitasTecnicas.business.*;
import co.com.claro.visitasTecnicas.entities.CityEntity;
import co.com.claro.visitastecnicas.ws.proxy.AddUnitRequest;
import co.com.claro.visitastecnicas.ws.proxy.CRUDUnitManagerResponse;
import co.com.claro.visitastecnicas.ws.proxy.PortManager;
import co.com.claro.visitastecnicas.ws.proxy.RequestCRUDUnit;
import co.com.claro.visitastecnicas.ws.proxy.UnitManagerResponse;
import co.com.telmex.catastro.data.AddressRequest;
import co.com.telmex.catastro.data.AddressService;
import co.com.telmex.catastro.data.Area;
import co.com.telmex.catastro.data.Comunidad;
import co.com.telmex.catastro.data.DetalleSolicitud;
import co.com.telmex.catastro.data.Direccion;
import co.com.telmex.catastro.data.Distrito;
import co.com.telmex.catastro.data.Divisional;
import co.com.telmex.catastro.data.EstadoHhpp;
import co.com.telmex.catastro.data.Geografico;
import co.com.telmex.catastro.data.GeograficoPolitico;
import co.com.telmex.catastro.data.Hhpp;
import co.com.telmex.catastro.data.HhppConsulta;
import co.com.telmex.catastro.data.Nodo;
import co.com.telmex.catastro.data.SolicitudRed;
import co.com.telmex.catastro.data.SubDireccion;
import co.com.telmex.catastro.data.TipoDireccion;
import co.com.telmex.catastro.data.TipoGeografico;
import co.com.telmex.catastro.data.TipoHhpp;
import co.com.telmex.catastro.data.TipoHhppConexion;
import co.com.telmex.catastro.data.TipoHhppRed;
import co.com.telmex.catastro.data.TipoUbicacion;
import co.com.telmex.catastro.data.Ubicacion;
import co.com.telmex.catastro.data.UnidadGestion;
import co.com.telmex.catastro.data.Zona;
import co.com.telmex.catastro.services.georeferencia.AddressEJB;
import co.com.telmex.catastro.services.georeferencia.AddressEJBRemote;
import co.com.telmex.catastro.services.hhpp.HhppEJBRemote;
import co.com.telmex.catastro.services.util.Constant;
import co.com.telmex.catastro.services.util.DireccionUtil;
import co.com.telmex.catastro.services.util.Parametros;
import co.com.telmex.catastro.services.util.ResourceEJB;
import com.jlcg.db.AccessData;
import com.jlcg.db.data.DataList;
import com.jlcg.db.data.DataObject;
import com.jlcg.db.exept.ExceptionDB;
import com.jlcg.db.sql.ManageAccess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 * Clase SolicitudRedEJB implementa SolicitudRedEJBRemote
 *
 * @author Ana María Malambo
 * @version	1.0
 */
@Stateless(name = "solicitudRedEJB", mappedName = "solicitudRedEJB", description = "solicitudRed")
@Remote({SolicitudRedEJBRemote.class})
public class SolicitudRedEJB implements SolicitudRedEJBRemote {

    private static final Logger LOGGER = LogManager.getLogger(SolicitudRedEJB.class);
    private String tipoHhp;
    private NodoManager manager;
    private static final String SOLICTUD_FICHA_NODO = "SOLICTUD FICHA NODO";
    @EJB
    private AddressEJBRemote addressEJB;
    @EJB
    private HhppEJBRemote hhppEJB;
    HHPPManager hhppManager = new HHPPManager();
    private static final int LONGITUD_TIPO_BOGOTA = 78;
    private static final int LONGITUD_TIPO_HERENDAN_BOGOTA_MEDELLIN = 79;
    private static final int LONGITUD_TIPO_CALI = 87;
    private static final int LONGITUD_TIPO_MAZANA_CASA = 99;
    private static String wsURL = "";
    private static String wsService = "";
    private CmtBasicaMgl estadoHhppExiste = new CmtBasicaMgl();
    private CmtTipoBasicaMglManager cmtTipoBasicaMglManager = new CmtTipoBasicaMglManager();
    private CmtBasicaMglManager cmtBasicaMglManager = new CmtBasicaMglManager();
    private CmtTipoBasicaMgl cmtTipoBasicaMgl = null;

    public SolicitudRedEJB() {
        queryParametrosConfig();
    }

    private void queryParametrosConfig() {

        ResourceEJB resourceEJB = new ResourceEJB();
        Parametros param;
        try {
            param = resourceEJB.queryParametros(Constant.PROPERTY_URL_WSRR);
            if (param != null) {
                wsURL = param.getValor();
            }
            param = resourceEJB.queryParametros(Constant.PROPERTY_SERVICE_WSRR);
            if (param != null) {
                wsService = param.getValor();
            }
            cmtTipoBasicaMgl = cmtTipoBasicaMglManager.findByCodigoInternoApp(
                    co.com.claro.mgl.utils.Constant.TIPO_BASICA_ESTADOS_HHPP);

            estadoHhppExiste = cmtBasicaMglManager.findByCodigoInternoApp(co.com.claro.mgl.utils.Constant.BASICA_EST_HHPP_TIPO_POTENCIAL);

        } catch (ApplicationException ex) {
            LOGGER.error("Error al consultar parametros de configuración. EX000 " + ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param detalles
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public BigDecimal createSolicitudPlaneacionRedMasivo(List<DetalleSolicitud> detalles) throws ApplicationException {
        NodoManager nodeManager = new NodoManager();
        AddressEJBRemote addressEJB;
        NegocioLocalidad negocioLocalidad = new NegocioLocalidad();
        BigDecimal idSre;
        BigDecimal idLocalidad;
        BigDecimal idBarrio;
        BigDecimal idManzana = null;
        BigDecimal idGeograficoUltimoNivel;
        String TipoHHPP;
        String nodoGuardar = Constant.STRING_VACIO;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            SolicitudRed sre = detalles.get(0).getSolicitudRed();
            Geografico geograficoLocalidad;
            Geografico geograficoBarrio;
            Geografico geograficoManzana;
            String barrioRR;
            sre.setSreEstado(Constant.ESTADO_RED_CREADA);
            idSre = createSolicitudRed(adb, sre);
            for (DetalleSolicitud dso : detalles) {
                Nodo node = nodeManager.queryNodo(dso.getNodo(), sre.getGeograficoPolitico().getGpoId());
                if (node != null) {
                    nodoGuardar = node.getNodId().toString();
                } else {
                    node = new Nodo();
                    node.setGeograficoPolitico(sre.getGeograficoPolitico());
                    node.setNodNombre(dso.getNodo());
                    node.setNodCodigo(dso.getNodo());
                    node.setNodFechaActivacion(new Date());
                    node.setNodFechaCreacion(new Date());
                    node.setNodFechaModificacion(new Date());
                    node.setNodUsuarioCreacion(Constant.STRING_VACIO);
                    node.setNodUsuarioModificacion(Constant.STRING_VACIO);
                    if (nodeManager.createNodo(node)) {
                        node = nodeManager.queryNodo(dso.getNodo(), sre.getGeograficoPolitico().getGpoId());
                        nodoGuardar = node.getNodId().toString();
                    } else {
                        throw new ApplicationException("Error creando nodo que no existente: " + dso.getNodo());
                    }
                }
                if (dso.getLocalidad().isEmpty()) {
                    geograficoLocalidad = negocioLocalidad.queryGeograficoLocalidadByNombre(
                            sre.getGeograficoPolitico().getGpoId().toString(),
                            sre.getGeograficoPolitico().getGpoNombre());
                    if (geograficoLocalidad == null) {
                        addressEJB = new AddressEJB();
                        geograficoLocalidad = new Geografico();
                        geograficoLocalidad.setGeoNombre(sre.getGeograficoPolitico().getGpoNombre());
                        geograficoLocalidad.setGeograficoPolitico(sre.getGeograficoPolitico());
                        geograficoLocalidad.setGeografico(new Geografico());
                        TipoGeografico TipoGeografico = new TipoGeografico();
                        TipoGeografico.setTgeId(Constant.ID_TGE_LOCALIDAD);
                        geograficoLocalidad.setTipoGeografico(TipoGeografico);
                        geograficoLocalidad.setGeoUsuarioCreacion("");
                        idLocalidad = addressEJB.insertGeograficoIntorepository(SOLICTUD_FICHA_NODO, geograficoLocalidad);
                        if (idLocalidad == null) {
                            throw new ApplicationException("Error al crear Localidad: " + sre.getGeograficoPolitico().getGpoNombre());
                        }
                    } else {
                        idLocalidad = geograficoLocalidad.getGeoId();
                    }

                } else {
                    geograficoLocalidad = negocioLocalidad.queryGeograficoLocalidadByNombre(
                            sre.getGeograficoPolitico().getGpoId().toString(),
                            dso.getLocalidad());
                    if (geograficoLocalidad == null) {
                        addressEJB = new AddressEJB();
                        geograficoLocalidad = new Geografico();
                        geograficoLocalidad.setGeoNombre(dso.getLocalidad());
                        geograficoLocalidad.setGeografico(new Geografico());
                        geograficoLocalidad.setGeograficoPolitico(sre.getGeograficoPolitico());
                        TipoGeografico TipoGeografico = new TipoGeografico();
                        TipoGeografico.setTgeId(Constant.ID_TGE_LOCALIDAD);
                        geograficoLocalidad.setTipoGeografico(TipoGeografico);
                        geograficoLocalidad.setGeoUsuarioCreacion(Constant.STRING_VACIO);
                        idLocalidad = addressEJB.insertGeograficoIntorepository(SOLICTUD_FICHA_NODO, geograficoLocalidad);
                        if (idLocalidad == null) {
                            throw new ApplicationException("Error al crear Localidad: " + dso.getLocalidad());
                        }
                    } else {
                        idLocalidad = geograficoLocalidad.getGeoId();
                    }
                }
                if (sre.getGeograficoPolitico().getGpoMultiorigen().equals(Constant.TIPO_CIUDAD_MULTIORIGEN)) {
                    String nomBarrio;
                    if (dso.getBarrioGeo().isEmpty()) {
                        nomBarrio = dso.getBarrio();
                    } else {
                        nomBarrio = dso.getBarrioGeo();
                    }
                    barrioRR = nomBarrio;
                    addressEJB = new AddressEJB();
                    idBarrio = addressEJB.queryGeograficoBarrioByIDLocalidad(idLocalidad.toString(), nomBarrio);
                    if (idBarrio == BigDecimal.ZERO) {
                        geograficoBarrio = new Geografico();
                        geograficoBarrio.setGeoNombre(nomBarrio);
                        geograficoLocalidad = new Geografico();
                        geograficoLocalidad.setGeoId(idLocalidad);
                        geograficoBarrio.setGeografico(geograficoLocalidad);
                        geograficoBarrio.setGeograficoPolitico(sre.getGeograficoPolitico());
                        geograficoBarrio.setGeoUsuarioCreacion(Constant.STRING_VACIO);
                        TipoGeografico TipoGeografico = new TipoGeografico();
                        TipoGeografico.setTgeId(Constant.ID_TGE_BARRIO);
                        geograficoBarrio.setTipoGeografico(TipoGeografico);
                        idBarrio = addressEJB.insertGeograficoIntorepository(SOLICTUD_FICHA_NODO, geograficoBarrio);
                        if (idBarrio == null) {
                            throw new ApplicationException("Error al crear barrio: " + nomBarrio);
                        }
                    }

                } else {
                    String nomBarrio;
                    if (dso.getBarrioGeo().isEmpty()) {
                        nomBarrio = "SIN BARRIO";
                    } else {
                        nomBarrio = dso.getBarrioGeo();
                    }
                    barrioRR = nomBarrio;
                    addressEJB = new AddressEJB();
                    idBarrio = addressEJB.queryGeograficoBarrioByIDLocalidad(idLocalidad.toString(), nomBarrio);
                    if (idBarrio == BigDecimal.ZERO) {
                        geograficoBarrio = new Geografico();
                        geograficoBarrio.setGeoNombre(nomBarrio);
                        geograficoLocalidad = new Geografico();
                        geograficoLocalidad.setGeoId(idLocalidad);
                        geograficoBarrio.setGeografico(geograficoLocalidad);
                        geograficoBarrio.setGeograficoPolitico(sre.getGeograficoPolitico());
                        geograficoBarrio.setGeoUsuarioCreacion(Constant.STRING_VACIO);
                        TipoGeografico TipoGeografico = new TipoGeografico();
                        TipoGeografico.setTgeId(Constant.ID_TGE_BARRIO);
                        geograficoBarrio.setTipoGeografico(TipoGeografico);
                        idBarrio = addressEJB.insertGeograficoIntorepository(SOLICTUD_FICHA_NODO, geograficoBarrio);
                        if (idBarrio == null) {
                            throw new ApplicationException("Error al crear barrio: " + nomBarrio);
                        }
                    }
                }
                if (dso.getServinformacion() != null) {
                    if (dso.getServinformacion().length() == Constant.LONGITUD_COD_SERVINFORMACION_BARRIO_MANZANA_CASA) {
                        if (dso.getServinformacion().substring(2, 2).equals(Constant.BARRIO_MANSANA_CASA)) {
                            String nomManzana;
                            if (dso.getServinformacion().substring(11, 12).matches("^(SM|SC)$")) {
                                nomManzana = dso.getServinformacion().substring(11, 18);
                            } else {
                                nomManzana = dso.getServinformacion().substring(19, 26);
                            }
                            idManzana = addressEJB.queryGeograficoMzaByIdBarrio(idBarrio.toString(), nomManzana);
                            if (idManzana == BigDecimal.ZERO) {
                                geograficoManzana = new Geografico();
                                geograficoManzana.setGeoNombre(nomManzana);
                                geograficoBarrio = new Geografico();
                                geograficoBarrio.setGeoId(idLocalidad);
                                geograficoManzana.setGeografico(geograficoBarrio);
                                geograficoManzana.setGeograficoPolitico(sre.getGeograficoPolitico());
                                geograficoManzana.setGeoUsuarioCreacion(Constant.STRING_VACIO);
                                TipoGeografico TipoGeografico = new TipoGeografico();
                                TipoGeografico.setTgeId(Constant.ID_TGE_MANZANA);
                                geograficoManzana.setTipoGeografico(TipoGeografico);
                                idManzana = addressEJB.insertGeograficoIntorepository(SOLICTUD_FICHA_NODO, geograficoManzana);
                                if (idManzana == null) {
                                    throw new ApplicationException("Error al crear Manzana: " + nomManzana);
                                }
                            }
                        }
                    }
                }
                if (idManzana == null) {
                    idGeograficoUltimoNivel = idBarrio;
                } else {
                    idGeograficoUltimoNivel = idManzana;
                }
                NegocioDireccionesRR negocioDireccionesRR = new NegocioDireccionesRR();
                String complementoRR;
                if (dso.getDireccionAComplemento().split("\\|").length > 1) {
                    complementoRR = dso.getDireccionAComplemento().split("\\|")[1];
                } else {
                    complementoRR = "";
                }
                if (dso.getDireccionAComplemento().toUpperCase().contains("APARTAMENTO")) {
                    TipoHHPP = "A";
                } else if (dso.getDireccionAComplemento().toUpperCase().contains("LOCAL")) {
                    TipoHHPP = "L";
                } else if (dso.getDireccionAComplemento().toUpperCase().contains("OFICINA")) {
                    TipoHHPP = "O";
                } else {
                    TipoHHPP = "C";
                }
                negocioDireccionesRR.calculateDireccion(dso.getServinformacion(), sre.getGeograficoPolitico().getGpoId(), barrioRR, complementoRR);
                adb.in("dso1",
                        idSre.toString(),
                        dso.getNombre(),
                        dso.getCalle(),
                        dso.getPlaca(),
                        dso.getBarrio(),
                        dso.getBarrioGeo(),
                        dso.getDireccionSta(),
                        dso.getDireccionAPlaca(),
                        dso.getDireccionAComplemento(),
                        dso.getDireccionAltSta(),
                        dso.getLocalidad(),
                        dso.getConfiabilidad().toString(),
                        dso.getExiste(),
                        dso.getFuenteuente(),
                        dso.getLatitud(),
                        dso.getLongitud(),
                        dso.getManzana(),
                        dso.getNivelSocioeconomico().toString(),
                        nodoGuardar,
                        dso.getServinformacion(),
                        dso.getTipoSolictud(),//null
                        dso.getZipcode(),
                        negocioDireccionesRR.getResultadoDirRRNumeroUnidad(),
                        negocioDireccionesRR.getResultadoDirRRCalle(),
                        negocioDireccionesRR.getResultadoDirRRNumeroApartamento(),
                        dso.getEstadoHHPP(),//null
                        dso.getActividadEconomica(),//null
                        dso.getValidadar(),//null
                        dso.getTipoRedHHPP().toString(),//null
                        dso.getTipoConexionHHPP().toString(),//null
                        idGeograficoUltimoNivel.toString(),
                        sre.getGeograficoPolitico().getGpoId().toString(),
                        TipoHHPP,//nul
                        dso.getUsuarioCreacion(),//null
                        dso.getUsuarioModificacion());//null
            }
            DireccionUtil.closeConnection(adb);
            return idSre;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            DireccionUtil.closeConnection(adb);
            return BigDecimal.ZERO;
        }
    }

    @Override
    public List<DetalleSolicitud> ConsultarSolicitudDeRed(BigDecimal idSolicitud) throws ExceptionDB, ApplicationException {
        List<DetalleSolicitud> detalleSolicitud = null;
        SolicitudRed solicitudRed;
        DetalleSolicitud reg;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject datosSolicitud = adb.outDataObjec("HeSol1", idSolicitud.toString());
            if (datosSolicitud != null) {
                solicitudRed = new SolicitudRed();
                solicitudRed.setSreId(datosSolicitud.getBigDecimal("SRE_ID"));
                solicitudRed.setGeograficoPolitico(queryGeograficoPoliticoId(datosSolicitud.getBigDecimal("GEOGRAFICO_POLITICO_ID")));
                solicitudRed.setSreObservaciones(datosSolicitud.getString("SRE_OBSERVACIONES"));
                solicitudRed.setSreEstado("SRE_ESTADO");
                DataList listaDatos = adb.outDataList("QuoSol1", idSolicitud.toString());

                if (listaDatos != null) {
                    detalleSolicitud = new ArrayList<DetalleSolicitud>();
                    for (DataObject obj : listaDatos.getList()) {
                        reg = new DetalleSolicitud();
                        reg.setDetalleSolicitudId(obj.getBigDecimal("DSO_ID"));
                        reg.setSolicitudRed(solicitudRed);
                        reg.setNombre(obj.getString("DSO_NOMBRE"));
                        reg.setCalle(obj.getString("DSO_CALLE"));
                        reg.setPlaca(obj.getString("DSO_PLACA"));
                        reg.setBarrio(obj.getString("GEO_BARRIOFICHA"));
                        reg.setBarrioGeo(obj.getString("GEO_BARRIOGEO"));
                        reg.setDireccionSta(obj.getString("DSO_DIRSTA"));
                        reg.setDireccionAPlaca(obj.getString("DSO_DIRECCION"));
                        reg.setDireccionAComplemento(obj.getString("DSO_COMPLEMENTO"));
                        reg.setDireccionAltSta(obj.getString("DSO_DIRALTSTA"));
                        reg.setLocalidad(obj.getString("GEO_LOCALIDAD"));
                        reg.setConfiabilidad(obj.getBigDecimal("DSO_CONFIABILIDAD"));
                        reg.setExiste(obj.getString("DSO_DIREXISTE"));
                        reg.setFuenteuente(obj.getString("DSO_FUENTE"));
                        reg.setLatitud(obj.getString("DSO_LATITUD"));
                        reg.setLongitud(obj.getString("DSO_LONGITUD"));
                        reg.setManzana(obj.getString("GEO_MANZANACATASTRAL"));
                        reg.setNivelSocioeconomico(new BigDecimal(obj.getString("DSO_NIVSOCIOECONOMICO")));
                        reg.setNodo(queryNodoById(obj.getBigDecimal("NODO_ID").toString()).getNodCodigo());
                        reg.setServinformacion(obj.getString("DSO_SERVINFORMACION"));
                        reg.setTipoSolictud(obj.getString("DSO_TIPOSOLICITUD"));
                        reg.setZipcode(obj.getString("DSO_ZIPCODE"));
                        reg.setNumeroUnidadRR(obj.getString("DSO_NUMEROUNIDADRR"));
                        reg.setCalleRR(obj.getString("DSO_CALLERR"));
                        reg.setNumeroAoartamentoRR(obj.getString("DSO_NUMEROAPARTAMENTORR"));
                        reg.setEstadoHHPP(obj.getString("DSO_ESTADOHHPP"));
                        reg.setActividadEconomica(obj.getString("DSO_ACTIVIDADECONOMICA"));
                        reg.setValidadar(obj.getString("DSO_VALIDAR"));
                        reg.setTipoRedHHPP(obj.getBigDecimal("THR_ID"));
                        reg.setTipoHhpp(obj.getString("THH_ID"));
                        reg.setTipoConexionHHPP(obj.getBigDecimal("THC_ID"));
                        /////////////////////////////////////////////////////
                        reg.setNombreTipoHhpp(obj.getString("NOMBRE_TIPO_HHPP"));
                        reg.setIdGeoUltimo(obj.getBigDecimal("GEO_ID"));
                        reg.setNombreTipoConexionHHPP(obj.getString("NOMBRE_TIPO_CONEXION"));
                        reg.setNombreTipoRedHHPP(obj.getString("NOMBRE_TIPO_HHPP"));
                        reg.setIdNodo(obj.getBigDecimal("NODO_ID"));
                        reg.setEstadoProceso(obj.getString("DSO_ESTADO_PROCESO"));
                        reg.setErrorProceso(obj.getString("DSO_ERROR_PROCESO") == null ? "" : obj.getString("DSO_ERROR_PROCESO"));
                        detalleSolicitud.add(reg);
                    }
                }

            }
            DireccionUtil.closeConnection(adb);
            return detalleSolicitud;
        } catch (ApplicationException | ExceptionDB e) {
            LOGGER.error(e.getMessage());
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar solicitud de red. EX000 " + e.getMessage(), e);
        }
    }

    @Override
    public List<DetalleSolicitud> procesarSolicitudDeRed(List<DetalleSolicitud> listaDetalleSolicitud) throws ExceptionDB, ApplicationException {
        try {
            //Actualiza los campos calle, #Unidad, #Apto en la tabla DETALLE_SOLICITUD
            actualizarDireccionesRR(listaDetalleSolicitud);
            //Crea HHPP en RR
            crearDireccionesRR(listaDetalleSolicitud);
            //Actualiza el campo DSO_ESTADO_PROCESO en la tabla DETALLE_SOLICITUD
            actualizarEstadoSolicitud(listaDetalleSolicitud);
            crearDireccionesRepositorio(listaDetalleSolicitud);
            //Actualiza el campo DSO_ESTADO_PROCESO en la tabla DETALLE_SOLICITUD
            actualizarEstadoSolicitud(listaDetalleSolicitud);
            //verificamos que todas las solicitudes esten finalizadas y cerramos la solicitud Padre
            cerrarSolicitud(listaDetalleSolicitud);
            return ConsultarSolicitudDeRed(listaDetalleSolicitud.get(0).getSolicitudRed().getSreId());
        } catch (IOException ex) {
            LOGGER.error("Error al procesar solicitud de red. EX000 " + ex.getMessage(), ex);
            throw new ApplicationException("Error al procesar solicitud de red. EX000 " + ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param adb
     * @return
     * @throws Exception
     */
    private BigDecimal getIdSolicitudRed(AccessData adb) throws ApplicationException {
        try {
            BigDecimal id = null;

            DataObject obj = adb.outDataObjec("sre2");
            if (obj != null) {
                id = obj.getBigDecimal("ID");
            }
            return id;
        } catch (ExceptionDB ex) {
            LOGGER.error("Error al traer el id de solicitud de red. EX000 " + ex.getMessage(), ex);
            throw new ApplicationException("Error al traer el id de solicitud de red. EX000 " + ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param adb
     * @param sre
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public BigDecimal createSolicitudRed(AccessData adb, SolicitudRed sre) throws ApplicationException {
        try {
            BigDecimal idsre;
            adb.in("sre1", sre.getGeograficoPolitico().getGpoId().toString(), sre.getSreObservaciones(),
                    sre.getSreEstado(), sre.getSreUsuarioCreacion());
            idsre = getIdSolicitudRed(adb);
            return idsre;
        } catch (ExceptionDB ex) {
            LOGGER.error("Error al crear la socitud de red. EX000 " + ex.getMessage(), ex);
            throw new ApplicationException("Error al crear la socitud de red. EX000 " + ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param codDir
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public BigDecimal consultaIdHHP(String codDir) throws ApplicationException {
        BigDecimal idHhp = BigDecimal.ZERO;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("dso6", codDir);
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                idHhp = obj.getBigDecimal("HHP_ID");
            }

            return idHhp;
        } catch (ExceptionDB e) {
            LOGGER.error(e.getMessage());
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar id HHPP. EX000 " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param codDir
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public BigDecimal consultaIdHHPSubDir(String codDir) throws ApplicationException {
        BigDecimal idHhpsub = BigDecimal.ZERO;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("dso7", codDir);
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                idHhpsub = obj.getBigDecimal("HHP_ID");
            }

            return idHhpsub;
        } catch (ExceptionDB e) {
            LOGGER.error(e.getMessage());
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar el id HHPP de la subdirección EX000 " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param geo_id
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public Geografico queryGeografico(BigDecimal geo_id) throws ApplicationException {
        Geografico geografico = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("geo1", geo_id.toString());
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                geografico = new Geografico();
                geografico.setGeoId(obj.getBigDecimal("GEO_ID"));
                geografico.setGeoNombre(obj.getString("GEO_NOMBRE"));
                BigDecimal gpo_id = obj.getBigDecimal("GEOGRAFICO_POLITICO_ID");
                if (gpo_id != null) {
                    geografico.setGeograficoPolitico(queryGeograficoPoliticoId(gpo_id));
                }
                TipoGeografico tipoG = new TipoGeografico();
                BigDecimal tge_id = obj.getBigDecimal("TGE_ID");
                geografico.setTipoGeografico(tipoG);
                BigDecimal geo_geo_id = obj.getBigDecimal("GEO_GEO_ID");
                Geografico geo_geo = new Geografico();
                if (geo_geo_id != null) {
                    geo_geo = queryGeografico(geo_geo_id);
                }
                geografico.setGeografico(geo_geo);
            }
            return geografico;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar geográfico. EX000 " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param gpo_id
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public GeograficoPolitico queryGeograficoPoliticoId(BigDecimal gpo_id) throws ApplicationException {
        GeograficoPolitico geograficoPo = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("gpo1", gpo_id.toString());
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                geograficoPo = new GeograficoPolitico();
                geograficoPo.setGpoId(obj.getBigDecimal("GEOGRAFICO_POLITICO_ID"));
                geograficoPo.setGpoNombre(obj.getString("GPO_NOMBRE"));
                geograficoPo.setGpoTipo(obj.getString("GPO_TIPO"));
                geograficoPo.setGpoCodigoDane(obj.getString("GPO_CODIGO"));
                BigDecimal geo_gpo_id = obj.getBigDecimal("GEO_GPO_ID");
                if (geo_gpo_id != null) {
                    GeograficoPolitico geo_gpo = queryGeograficoPoliticoId(geo_gpo_id);
                    geograficoPo.setGeograficoPolitico(geo_gpo);
                }
            }
            return geograficoPo;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar el id geográficio politico. EX000 " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param gpo_name
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public GeograficoPolitico queryGeograficoPolitico(String gpo_name) throws ApplicationException {
        GeograficoPolitico geograficoPo = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("gpo2", gpo_name);
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                geograficoPo = new GeograficoPolitico();
                geograficoPo.setGpoId(obj.getBigDecimal("GEOGRAFICO_POLITICO_ID"));
                geograficoPo.setGpoNombre(obj.getString("GPO_NOMBRE"));
                geograficoPo.setGpoTipo(obj.getString("GPO_TIPO"));
                geograficoPo.setGpoCodigoDane(obj.getString("GPO_CODIGO"));
                BigDecimal geo_gpo_id = obj.getBigDecimal("GEO_GPO_ID");
                if (geo_gpo_id != null) {
                    GeograficoPolitico geo_gpo = queryGeograficoPoliticoId(geo_gpo_id);
                    geograficoPo.setGeograficoPolitico(geo_gpo);
                }
            }
            return geograficoPo;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar geografico politico EX000 " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param uge_id
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public List<SolicitudRed> querySolicitudesRedPendientes() throws ApplicationException {
        List<SolicitudRed> solicitudes = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("sre3", Constant.ESTADO_SPLRED_INICIAL);
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                solicitudes = new ArrayList<SolicitudRed>();
                for (DataObject obj : list.getList()) {
                    //SRE_ID,GPO_ID,SRE_OBSERVACIONES,SRE_FECHA_CREACION,SRE_USUARIO_CREACION,SRE_ESTADO
                    BigDecimal id = obj.getBigDecimal("SRE_ID");
                    String observaciones = obj.getString("SRE_OBSERVACIONES");
                    String fecha_crea = obj.getString("SRE_FECHA_CREACION");
                    String usu_creacion = obj.getString("SRE_USUARIO_CREACION");
                    String estado = obj.getString("SRE_ESTADO");
                    Date fechaC = new Date();
                    int day = Integer.parseInt(fecha_crea.substring(0, 1));
                    int month = Integer.parseInt(fecha_crea.substring(3, 4));
                    int year = Integer.parseInt(fecha_crea.substring(6, 9));
                    fechaC.setDate(day);
                    fechaC.setMonth(month);
                    fechaC.setYear(year);
                    fechaC.setHours(Integer.parseInt(fecha_crea.substring(11, 12)));
                    fechaC.setHours(Integer.parseInt(fecha_crea.substring(14, 15)));
                    SolicitudRed solicitud = new SolicitudRed();
                    solicitud.setSreId(id);
                    solicitud.setSreObservaciones(observaciones);
                    solicitud.setSreFechaCreacion(fechaC);
                    solicitud.setSreUsuarioCreacion(usu_creacion);
                    solicitud.setSreEstado(estado);
                    solicitudes.add(solicitud);
                }
            }
            return solicitudes;
        } catch (ExceptionDB | NumberFormatException e) {
            LOGGER.error(e.getMessage());
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultat solicitudes de red pendientes. EX000 " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param idSre
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public int queryQDetalleSolicitud(BigDecimal idSre) throws ApplicationException {
        int qHhpp = 0;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("dso2", idSre.toString());
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                qHhpp = (obj.getBigDecimal("QDSO")).intValue();
            }
            return qHhpp;
        } catch (ExceptionDB e) {
            LOGGER.error(e.getMessage());
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar el detalle de la solicitud. EX000 " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param idNodo
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public Nodo queryNodoById(String idNodo) throws ApplicationException {
        Nodo nodo = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("nod5", idNodo);
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                ConsultaSolicitudEJB consultaEJB = new ConsultaSolicitudEJB();
                nodo = new Nodo();
                nodo.setNodId(obj.getBigDecimal("NODO_ID"));
                GeograficoPolitico geograficoPo;
                BigDecimal gpo_idN = obj.getBigDecimal("GEOGRAFICO_POLITICO_ID");
                if (gpo_idN != null) {
                    geograficoPo = queryGeograficoPoliticoId(gpo_idN);
                    nodo.setGeograficoPolitico(geograficoPo);
                }
                nodo.setNodNombre(obj.getString("NOMBRE"));
                nodo.setNodCodigo(obj.getString("CODIGO"));
                UnidadGestion ugestion = new UnidadGestion();
                BigDecimal uge_id = obj.getBigDecimal("UNIDAD_GESTION_BASICA_ID");
                if (uge_id != null) {
                    ugestion = new UnidadGestion();
                    nodo.setUnidadGestion(ugestion);
                }
                Zona zona;
                BigDecimal zon_id = obj.getBigDecimal("ZONA_BASICA_ID");
                if (zon_id != null) {
                    zona = new Zona();
                    nodo.setZona(zona);
                }
                Distrito distrito;
                BigDecimal dis_id = obj.getBigDecimal("DISTRITO_BASICA_ID");
                if (dis_id != null) {
                    distrito = manager.queryDistrito(dis_id);
                    nodo.setDistrito(distrito);
                }
                Divisional divisional;
                BigDecimal div_id = obj.getBigDecimal("DIVISIONAL_BASICA_ID");
                if (div_id != null) {
                    divisional = manager.queryDivisional(div_id);
                    nodo.setDivisional(divisional);
                }
                Area area;
                BigDecimal are_id = obj.getBigDecimal("AREA_BASICA_ID");
                if (are_id != null) {
                    area = new Area();
                    nodo.setArea(area);
                }
                String nod_headEnd = obj.getString("HEAD_END");
                nodo.setNodHeadend(nod_headEnd);
                BigDecimal nod_Tipo = obj.getBigDecimal("TIPO");
                nodo.setNodTipo(nod_Tipo);
                String id_com = obj.getString("COM_ID");
                Comunidad comunidad;
                if (id_com != null) {
                    comunidad = consultaEJB.queryComunidad(id_com);
                    nodo.setComunidad(comunidad);
                }
            }
            return nodo;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultr nodo por Id. EX000 " + e.getMessage(), e);
        }
    }

    /**
     * Consultar una solicitud de Red
     *
     * @param idSolicitud
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public SolicitudRed querySolicitudNegocio(BigDecimal idSolicitud) throws ApplicationException {
        SolicitudRed solicitudR = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("sre14", idSolicitud.toString());
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                solicitudR = new SolicitudRed();
                ConsultaSolicitudEJB consultaEjb = new ConsultaSolicitudEJB(); 

                solicitudR.setSreId(obj.getBigDecimal("SRE_ID"));
                BigDecimal id_gpo = obj.getBigDecimal("GEOGRAFICO_POLITICO_ID");
                GeograficoPolitico gpo = null;
                if (id_gpo != null) {
                    gpo = consultaEjb.queryGeograficoPolitico(id_gpo);
                }

                solicitudR.setGeograficoPolitico(gpo);
                solicitudR.setSreObservaciones(obj.getString("SRE_OBSERVACIONES"));
                solicitudR.setSreFechaCreacion(new Date(obj.getString("SRE_FECHA_CREACION")));
                solicitudR.setSreUsuarioCreacion(obj.getString("SRE_USUARIO_CREACION"));
                solicitudR.setSreFechaModificacion(new Date(obj.getString("SRE_FECHA_MODIFICACION")));
                solicitudR.setSreUsuarioModificacion(obj.getString("SRE_USUARIO_MODIFICACION"));
                solicitudR.setSreEstado(obj.getString("SRE_ESTADO"));
            }
            return solicitudR;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar solicitud de negocio. EX000 " + e.getMessage(), e);
        }
    }

    @Override
    public Nodo queryNodo(String codigoNodo, BigDecimal gpo_id) throws ApplicationException {
        return manager.queryNodo(codigoNodo, gpo_id);
    }

    private void actualizarDireccionesRR(List<DetalleSolicitud> listaDetalleSolicitud) throws ExceptionDB, ApplicationException {
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            for (DetalleSolicitud dso : listaDetalleSolicitud) {
                adb.in("UpdDetSol", dso.getCalleRR(), dso.getNumeroUnidadRR(), dso.getNumeroAoartamentoRR(), dso.getDetalleSolicitudId().toString());
            }
            DireccionUtil.closeConnection(adb);
        } catch (ExceptionDB ex) {
            LOGGER.error(ex.getMessage());
            DireccionUtil.closeConnection(adb);
            throw ex;
        } catch (Exception x) {
            LOGGER.error(x.getMessage());
            DireccionUtil.closeConnection(adb);
            throw x;
        }
    }

    private void actualizarEstadoSolicitud(List<DetalleSolicitud> listaDetalleSolicitud) throws ExceptionDB, ApplicationException {
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            for (DetalleSolicitud dso : listaDetalleSolicitud) {
                if (dso.getEstadoProceso().equalsIgnoreCase(Constant.ESTADO_PROCESO_RED_REPOSITORIO)) {
                    dso.setEstadoProceso(Constant.ESTADO_PROCESO_RED_TERMINADA);
                }
                String errorProceso = dso.getErrorProceso() == null ? "" : dso.getErrorProceso();
                adb.in("UpdDSEst", dso.getEstadoProceso(), errorProceso, dso.getDetalleSolicitudId().toString());
            }
            DireccionUtil.closeConnection(adb);
        } catch (ExceptionDB ex) {
            LOGGER.error(ex.getMessage());
            DireccionUtil.closeConnection(adb);
            throw ex;
        } catch (Exception x) {
            LOGGER.error(x.getMessage());
            DireccionUtil.closeConnection(adb);
            throw x;
        }
    }

    private void crearDireccionesRR(List<DetalleSolicitud> listaDetalleSolicitud) throws ExceptionDB {
        String nivelSocioEconomico;
        for (DetalleSolicitud dso : listaDetalleSolicitud) {
            try {
                if (dso.getEstadoProceso().toUpperCase().equals(Constant.ESTADO_PROCESO_RED_RR)) {
                    continue;
                }

                NodoManager nodoManager = new NodoManager();
                NodoRR nodoRR = nodoManager.queryNodoRR(dso.getNodo());
                String comunidad = nodoRR.getCodCiudad();
                String division = nodoRR.getCodRegional();
                //previamente se debe realizar la creacion de la calle
                if (crearStreetHHPPRR(comunidad, division, dso.getCalleRR())) {

                    AddUnitRequest unitRequest = new AddUnitRequest();
                    String estUnit = cmtBasicaMglManager.findValorExtendidoEstHhpp(estadoHhppExiste);
                    unitRequest.setUnitStatus(estUnit);
                    nivelSocioEconomico = generarEstratoRR(dso.getNivelSocioeconomico().toBigInteger().toString(),
                            dso.getNumeroAoartamentoRR());

                    unitRequest.setStratus(nivelSocioEconomico != null && !nivelSocioEconomico.trim().isEmpty() ? nivelSocioEconomico : "NG");

                    unitRequest.setDropType("A");
                    unitRequest.setDropTypeCable("11");

                    unitRequest.setPlantLocType(dso.getTipoHhpp());

                    unitRequest.setCommunity(comunidad);
                    unitRequest.setDivision(division);
                    unitRequest.setGridPosition(comunidad);

                    DireccionRRManager direccionRRManager = new DireccionRRManager(true);
                    String dirHeadEnd = direccionRRManager.traerHeadEnd(comunidad);
                    unitRequest.setHeadEnd(dirHeadEnd == null ? "" : dirHeadEnd);

                    unitRequest.setPlantLocType("ND");
                    unitRequest.setPlantLocation(dso.getNodo());

                    NegocioParamMultivalor negocioMultivalor = new NegocioParamMultivalor();

                    CityEntity cityEntity = negocioMultivalor.consultaRRCiudadByCodCidadCodReg(comunidad, division);
                    if (cityEntity != null) {
                        unitRequest.setPostalCode(cityEntity.getPostalCode());
                    } else {
                        unitRequest.setPostalCode("000");
                    }

                    unitRequest.setDealer("9999");

                    Date toDay = new Date();
                    DateFormat df = new SimpleDateFormat("yyyyMMdd");
                    unitRequest.setAuditCompletedDate(df.format(toDay));
                    unitRequest.setApartmentNumber(dso.getNumeroAoartamentoRR());
                    unitRequest.setStreetName(dso.getCalleRR());
                    unitRequest.setHouseNumber(dso.getNumeroUnidadRR());

                    unitRequest.setUnitType(dso.getNumeroAoartamentoRR().contains("LC")
                            ? Constant.TIPO_UNIDAD_COMERCIAL : dso.getTipoHhpp());

                    String barrio;
                    if (dso.getBarrio() != null && dso.getBarrio().trim().equals(Constant.VACIO_RR)
                            && dso.getBarrioGeo() != null
                            && dso.getBarrioGeo().trim().equals(Constant.VACIO_RR)) {
                        barrio = "NG";
                    } else if (dso.getBarrioGeo() != null
                            && !dso.getBarrioGeo().trim().equals(Constant.VACIO_RR)) {
                        barrio = dso.getBarrioGeo();
                    } else {
                        barrio = dso.getBarrio() == null ? "NG" : dso.getBarrio();
                    }

                    String buildingName = generarEdificio(barrio == null
                            || barrio.trim().isEmpty() ? "NG" : barrio);
                    unitRequest.setBuildingName(buildingName);

                    unitRequest.setCaby("COMEX");
                    unitRequest.setTypeRequest("ADDUNIT");

                    PortManager portManager = new PortManager(wsURL, wsService);
                    UnitManagerResponse respuesta = portManager.addUnit(unitRequest);
                    if (respuesta.getResponseAddUnit().getMessageText().toUpperCase().contains("CAB0075")) {
                        dso.setEstadoProceso(Constant.ESTADO_PROCESO_RED_RR);
                        dso.setErrorProceso("");
                    } else {
                        dso.setErrorProceso(respuesta.getResponseAddUnit().getMessageText().replace("'", ""));
                    }
                }
            } catch (Exception ex) {
                LOGGER.error("Se produjo un error al momento de ejecutar el método .'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ ex.getMessage());
                dso.setErrorProceso("Error al consumir el WS de HHPP RR: " + ex.getMessage().replace("'", ""));
            }
        }
    }

    private boolean crearStreetHHPPRR(String comunidad, String division, String streetRR) throws ApplicationException {
        try {
            RequestCRUDUnit requestCRUDUnit = new RequestCRUDUnit();

            requestCRUDUnit.setCaby("COMEX");
            requestCRUDUnit.setComunity(comunidad);
            requestCRUDUnit.setDivision(division);
            requestCRUDUnit.setStreetName(streetRR);
            requestCRUDUnit.setMode("CREATE");

            PortManager portManager = new PortManager(wsURL, wsService);

            CRUDUnitManagerResponse response = portManager.addStreet(requestCRUDUnit);

            if (response.getResponseCRUDUnit().getMessageText().trim().toUpperCase().contains("(CAB0075)")
                    || response.getResponseCRUDUnit().getMessageText().trim().toUpperCase().contains("(CAB0218)")) {
                return true;
            } else {
                throw new ApplicationException("Error al Crear la calle: "
                        + streetRR + " ,error RR:" + response.getResponseCRUDUnit().getMessageText());
            }
        } catch (ApplicationException ex) {
           LOGGER.error("Se produjo un error al momento de ejecutar el método .'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ ex.getMessage());
            throw ex;
        }
    }

    private void crearDireccionesRepositorio(List<DetalleSolicitud> listaDetalleSolicitud) throws IOException, ApplicationException {

        Ubicacion ubicacion;
        Geografico geografico = null;
        String codigoServiAplaca;
        String codigoServiComplemnto;
        Direccion direccion;
        SubDireccion subDireccion;
        addressEJB = new AddressEJB();
        ConsultaCodigoMasiva(listaDetalleSolicitud);
        for (DetalleSolicitud dso : listaDetalleSolicitud) {
            geografico = null;
            codigoServiAplaca = "";
            codigoServiComplemnto = "";
            subDireccion = null;
            try {
                if (dso.getEstadoProceso().equalsIgnoreCase(Constant.ESTADO_PROCESO_RED_RR)) {
                    geografico = new Geografico();
                    BigDecimal idUbicacion = addressEJB.validarCxCyOnRepository(dso.getLatitud(), dso.getLongitud());
                    if (idUbicacion.compareTo(BigDecimal.ZERO) == 0) {
                        ubicacion = new Ubicacion();
                        ubicacion.setUbiId(BigDecimal.ZERO);
                        ubicacion.setGeoId(queryGeografico(dso.getIdGeoUltimo()));
                        ubicacion.setGpoId(dso.getSolicitudRed().getGeograficoPolitico());
                        ubicacion.setUbiLatitud(dso.getLatitud());
                        ubicacion.setUbiLongitud(dso.getLongitud());
                        ubicacion.setUbiNombre(dso.getNombre());
                        ubicacion.setUbiDistritoCodigoPostal("0000");
                        ubicacion.setUbiEstadoRed("E");
                        ubicacion.setUbiZonaDivipola("000");
                        TipoUbicacion tipoUbicacion = new TipoUbicacion();
                        tipoUbicacion.setTubId(new BigDecimal("2"));
                        ubicacion.setTubId(tipoUbicacion);
                        ubicacion.setUbiCuentaMatriz("false");
                        ubicacion.setUbiId(addressEJB.insertUbicacion("PROCESAR CREACION RED", ubicacion));
                    } else {
                        ubicacion = addressEJB.queryUbicacionById(idUbicacion);
                    }
                    direccion = null;
                    if (dso.getServinformacion().length() == LONGITUD_TIPO_BOGOTA) {
                        direccion = addressEJB.queryAddressOnRepository(dso.getServinformacion().substring(0, 31),null);
                        codigoServiAplaca = dso.getServinformacion().substring(0, 31);
                        codigoServiComplemnto = dso.getDireccionEstandarizadaComplemnto().substring(31);
                    } else if (dso.getServinformacion().length() == LONGITUD_TIPO_CALI) {
                        direccion = addressEJB.queryAddressOnRepository(dso.getServinformacion().substring(0, 40),null);
                        codigoServiAplaca = dso.getServinformacion().substring(0, 40);
                        codigoServiComplemnto = dso.getDireccionEstandarizadaComplemnto().substring(40);
                    } else if (dso.getServinformacion().length() == LONGITUD_TIPO_HERENDAN_BOGOTA_MEDELLIN) {
                        direccion = addressEJB.queryAddressOnRepository(dso.getServinformacion().substring(0, 32),null);
                        codigoServiAplaca = dso.getServinformacion().substring(0, 32);
                        codigoServiComplemnto = dso.getDireccionEstandarizadaComplemnto().substring(32);
                    } else if (dso.getServinformacion().length() == LONGITUD_TIPO_MAZANA_CASA) {
                        direccion = addressEJB.queryAddressOnRepository(dso.getServinformacion().substring(0, 51),null);
                        codigoServiAplaca = dso.getServinformacion().substring(0, 51);
                        codigoServiComplemnto = dso.getDireccionEstandarizadaComplemnto().substring(51);
                    } else {
                        throw new ApplicationException("Codigo direccion no concuerda con ningun tipo");
                    }

                    if (direccion == null) {
                        direccion = new Direccion();
                        direccion.setDirId(BigDecimal.ZERO);
                        direccion.setUbicacion(ubicacion);
                        direccion.setCx(dso.getLongitud());
                        direccion.setCy(dso.getLatitud());
                        direccion.setDirActividadEconomica(dso.getActividadEconomica());
                        direccion.setDirComentarioNivelSocioEconomico("");
                        direccion.setDirConfiabilidad(dso.getConfiabilidad());
                        direccion.setDirEstrato(co.com.claro.mgl.utils.Constant.ESTRATO_DIRECCION_NG);
                        direccion.setDirFechaModificacion(new Date());
                        direccion.setDirFormatoIgac(codigoServiAplaca);
                        direccion.setDirId(BigDecimal.ZERO);
                        direccion.setDirManzanaCatastral(dso.getManzanaCatastral());
                        direccion.setDirNivelSocioecono(dso.getNivelSocioeconomico());
                        direccion.setDirNostandar(dso.getDireccionAPlaca());
                        direccion.setDirOrigen(dso.getFuenteuente());
                        direccion.setDirRevisar("0");
                        direccion.setDirUsuarioCreacion("");
                        direccion.setDirUsuarioModificacion("");
                        direccion.setNodoDos(dso.getNodo());
                        TipoDireccion tipoDireccion = new TipoDireccion();
                        tipoDireccion.setTdiId(BigDecimal.ONE);
                        direccion.setTipoDireccion(tipoDireccion);
                        direccion.setUbicacion(ubicacion);
                        BigDecimal idDir = addressEJB.insertAddressOnRepository("PROCESAR CREACION RED", direccion);
                        direccion.setDirId(idDir);

                    }

                    if (!codigoServiComplemnto.matches("[0]+")) {
                        subDireccion = addressEJB.querySubAddressOnRepoByCod(dso.getDireccionEstandarizadaComplemnto(),null);
                        if (subDireccion == null) {
                            subDireccion = new SubDireccion();
                            subDireccion.setDireccion(direccion);
                            subDireccion.setCx(dso.getLongitud());
                            subDireccion.setCy(dso.getLatitud());
                            subDireccion.setNodoUno(dso.getNodo());
                            subDireccion.setSdiCodigoPostal("");
                            subDireccion.setSdiComentarioNivelSocioeconomico("");
                            subDireccion.setSdiFormatoIgac(dso.getCodDireccionAComplemnto());
                            subDireccion.setSdiId(BigDecimal.ZERO);
                            subDireccion.setSdiNivelSocioecono(dso.getNivelSocioeconomico());
                            subDireccion.setSdiServinformacion(dso.getDireccionEstandarizadaComplemnto());
                            subDireccion.setSdiFechaCreacion(new Date());
                            subDireccion.setSdiFechaModificacion(new Date());
                            subDireccion.setSdiUsuarioCreacion("");
                            subDireccion.setSdiUsuarioCreacion("");
                            subDireccion.setSdiId(addressEJB.insertSubAddressOnRepository("PROCESAR CREACION RED", subDireccion));
                        }
                    }
                    Hhpp hhpp = new Hhpp();
                    hhpp.setDireccion(direccion);
                    EstadoHhpp estadoHhpp = new EstadoHhpp();
                    if (dso.getEstadoHHPP().length() == 1) {
                        estadoHhpp.setEhhId(cmtBasicaMglManager.findCodigoOrExtEstHhpp(cmtTipoBasicaMgl, dso.getEstadoHHPP(), true));
                    } else {
                        estadoHhpp.setEhhId(dso.getEstadoHHPP());
                    }
                    hhpp.setEstadoHhpp(estadoHhpp);
                    hhpp.setHhppId(BigDecimal.ZERO);
                    Nodo nodo = new Nodo();
                    nodo.setNodId(dso.getIdNodo());
                    hhpp.setNodo(nodo);
                    hhpp.setSubDireccion(subDireccion);
                    TipoHhppConexion tipoHhppConexion = new TipoHhppConexion();
                    tipoHhppConexion.setThcId(dso.getTipoConexionHHPP());
                    hhpp.setTipoConexionHhpp(tipoHhppConexion);
                    TipoHhpp tipoHHPP = new TipoHhpp();
                    tipoHHPP.setThhId(dso.getTipoHhpp());
                    hhpp.setTipoHhpp(tipoHHPP);
                    TipoHhppRed tipoRedHhpp = new TipoHhppRed();
                    tipoRedHhpp.setThrId(dso.getTipoRedHHPP());
                    hhpp.setTipoRedHhpp(tipoRedHhpp);
                    if (subDireccion == null) {
                        List<HhppConsulta> hhpps = hhppEJB.queryHhppByIdDir(direccion.getDirServinformacion());
                        if (hhpps != null) {
                            for (HhppConsulta hhppConsulta : hhpps) {
                                if (hhppConsulta.getIdTipoRed().compareTo(hhpp.getTipoRedHhpp().getThrId()) == 0) {
                                    throw new ApplicationException("Ya existe un HHPP del mismo tipo de red para esta dirección");
                                }
                            }
                        }
                        hhppManager.persistHhpp(hhpp, "PROCESAR CREACION RED");
                        dso.setEstadoProceso(Constant.ESTADO_PROCESO_RED_REPOSITORIO);
                    } else {
                        List<HhppConsulta> hhpps = hhppEJB.queryHhppByIdSubDir(subDireccion.getSdiServinformacion());
                        if (hhpps != null) {
                            for (HhppConsulta hhppConsulta : hhpps) {
                                if (hhppConsulta.getIdTipoRed().compareTo(hhpp.getTipoRedHhpp().getThrId()) == 0) {
                                    throw new ApplicationException("Ya existe un HHPP del mismo tipo de red para esta dirección Subdireccion");
                                }
                            }
                        }
                        hhppManager.persistHhpp(hhpp, "PROCESAR CREACION RED");
                        dso.setEstadoProceso(Constant.ESTADO_PROCESO_RED_REPOSITORIO);
                    }
                    dso.setErrorProceso("");
                }
            } catch (NullPointerException ex) {
                LOGGER.error("Error al crear direcciones. EX000 " + ex.getMessage(), ex);
                dso.setErrorProceso("Error al crear direcciones. " + ex.getMessage().replace("'", ""));
            } catch (ApplicationException e) {
                LOGGER.error("Error al crear direcciones. EX000 " + e.getMessage(), e);
                dso.setErrorProceso("Error al al crear direcciones. " + e.getMessage().replace("'", ""));
            }
        }
    }

    private void ConsultaCodigoMasiva(List<DetalleSolicitud> listaDetalleSolicitud) throws ApplicationException {
        AddressRequest addressRequest;
        for (DetalleSolicitud dso : listaDetalleSolicitud) {
            if (dso.getEstadoProceso().equalsIgnoreCase(Constant.ESTADO_PROCESO_RED_RR)) {
                addressRequest = new AddressRequest();
                addressRequest.setId(dso.getDetalleSolicitudId().toString());
                addressRequest.setCity(dso.getSolicitudRed().getGeograficoPolitico().getGpoNombre());
                addressRequest.setState(dso.getSolicitudRed().getGeograficoPolitico().getGeograficoPolitico().getGpoNombre());
                addressRequest.setLevel("C");
                addressRequest.setAddress(dso.getDireccionAComplemento().replace("|", ""));
                AddressService addressService = addressEJB.queryAddress(addressRequest);
                dso.setDireccionEstandarizadaComplemnto(addressService.getAddressCode());
                dso.setCodDireccionAComplemnto(addressService.getAddress());
            }
        }
    }

    private void cerrarSolicitud(List<DetalleSolicitud> listaDetalleSolicitud) throws ApplicationException {
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            String idSol = "";
            if (listaDetalleSolicitud != null && listaDetalleSolicitud.size() > 0) {
                idSol = listaDetalleSolicitud.get(0).getSolicitudRed().getSreId().toString();
            }
            String numSolicitudes = "1";
            //consultamos el numero de solicitudes que aun no se ha finalizado

            if (idSol != null && !idSol.trim().isEmpty()) {
                DataObject obj = adb.outDataObjec("countSolNoFinalizada", idSol);
                if (obj != null) {
                    numSolicitudes = obj.getBigDecimal("SOLICITUDES").toBigInteger().toString();
                }
            }
            //si todas las solicitudes se han finalizado, cerramos la Solicitud padre
            if (numSolicitudes != null
                    && !numSolicitudes.trim().isEmpty()
                    && numSolicitudes.equalsIgnoreCase("0")) {
                adb.in("uptSolRedProcesada", idSol);
            }
        } catch (ExceptionDB e) {
            LOGGER.error(e.getMessage());
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al crear la solicitud. EX000 " + e.getMessage(), e);
        } finally {
            DireccionUtil.closeConnection(adb);
        }
    }

    private String generarEdificio(String barrio) {

        String[] palabras = barrio.split(" ");
        String edificio = "";
        if (palabras.length == 1) {
            edificio = barrio;
        } else if (palabras.length == 2) {
            edificio = (palabras[0]) + " " + palabras[1];
        } else if (palabras.length > 2) {
            edificio = palabras[0] + " ";
            for (int i = 1; i < palabras.length; i++) {
                edificio += palabras[i];
            }
        }
        return edificio;
    }

    private String generarEstratoRR(String estrato, String numApto) {

        String nivelSocioEconomico = "";
        if (estrato == null || estrato.trim().isEmpty()) {
            nivelSocioEconomico = Constant.NIVEL_SOCIO_NO_GEO;
        } else if (estrato.trim().equalsIgnoreCase(Constant.NIVEL_SOCIO_COMERCIAL)
                || numApto.contains("LC")) {
            nivelSocioEconomico = Constant.NIVEL_SOCIO_COMERCIAL_RR;
        }
        return nivelSocioEconomico;
    }

}
