package co.com.telmex.catastro.services.solicitud;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.visitasTecnicas.business.NodoManager;
import co.com.telmex.catastro.data.Area;
import co.com.telmex.catastro.data.Comunidad;
import co.com.telmex.catastro.data.Distrito;
import co.com.telmex.catastro.data.Divisional;
import co.com.telmex.catastro.data.Geografico;
import co.com.telmex.catastro.data.GeograficoPolitico;
import co.com.telmex.catastro.data.Hhpp;
import co.com.telmex.catastro.data.Marcas;
import co.com.telmex.catastro.data.Multivalor;
import co.com.telmex.catastro.data.Nodo;
import co.com.telmex.catastro.data.SolicitudNegocio;
import co.com.telmex.catastro.data.TipoHhpp;
import co.com.telmex.catastro.data.TipoHhppConexion;
import co.com.telmex.catastro.data.TipoHhppRed;
import co.com.telmex.catastro.data.TipoMarcas;
import co.com.telmex.catastro.data.UnidadGestion;
import co.com.telmex.catastro.data.Zona;
import co.com.telmex.catastro.services.georeferencia.AddressEJB;
import co.com.telmex.catastro.services.hhpp.HhppEJB;
import co.com.telmex.catastro.services.seguridad.AuditoriaEJB;
import co.com.telmex.catastro.services.util.Constant;
import co.com.telmex.catastro.services.util.DireccionUtil;
import co.com.telmex.catastro.services.util.EnvioCorreo;
import com.jlcg.db.AccessData;
import com.jlcg.db.data.DataList;
import com.jlcg.db.data.DataObject;
import com.jlcg.db.exept.ExceptionDB;
import com.jlcg.db.sql.ManageAccess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 * Clase SolicitudNegocioEJB implementa SolicitudNegocioEJBRemote
 *
 * @author Nataly Orozco Torres
 * @version	1.0
 */
@Stateless(name = "solicitudNegocioEJB", mappedName = "solicitudNegocioEJB", description = "solicitudNegocio")
@Remote({SolicitudNegocioEJBRemote.class})
public class SolicitudNegocioEJB implements SolicitudNegocioEJBRemote {

    NodoManager nodoManager = new NodoManager();
    private static final Logger LOGGER = LogManager.getLogger(SolicitudNegocioEJB.class);

    /**
     *
     * @param nombreFuncionalidad
     * @param solicitud
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public BigDecimal insertSolicitudNegocio(String nombreFuncionalidad, SolicitudNegocio solicitud) throws ApplicationException {
        BigDecimal idson = null;
        AccessData adb = null;
        String hhpp_id = null;
        String geografico = null;
        String geoPolitico = null;
        String nodo = null;
        String tipohhpp = null;
        String tipoRed = null;
        String tipoConexion = null;
        String marca = null;
        String confiabilidad = null;
        try {
            adb = ManageAccess.createAccessData();
            if (solicitud.getHhpp() != null) {
                hhpp_id = solicitud.getHhpp().getHhppId().toString();
            }

            if (solicitud.getGeograficoPolitico() != null) {
                geoPolitico = solicitud.getGeograficoPolitico().getGpoId().toString();
            }
            if (solicitud.getNodo() != null) {
                nodo = solicitud.getNodo().getNodId().toString();
            }
            if (solicitud.getTipoHhpp() != null) {
                tipohhpp = solicitud.getTipoHhpp().getThhId().toString();
            }
            if (solicitud.getGeografico() != null) {
                geografico = solicitud.getGeografico().getGeoId().toString();
            }
            if (solicitud.getTipoHhppRed() != null) {
                tipoRed = solicitud.getTipoHhppRed().getThrId().toString();
            }
            if (solicitud.getTipoHhppConexion() != null) {
                tipoConexion = solicitud.getTipoHhppConexion().getThcId().toString();
            }
            if (solicitud.getMarcas() != null) {
                marca = solicitud.getMarcas().getMarId().toString();
            }
            if (solicitud.getSonConfiabilidad() != null) {
                confiabilidad = solicitud.getSonConfiabilidad().toString();
            }
            adb.in("son1",
                    hhpp_id,
                    geoPolitico,
                    nodo,
                    tipohhpp,
                    geografico,
                    tipoRed,
                    tipoConexion,
                    marca,
                    solicitud.getSonMotivo(),
                    solicitud.getSonNomSolicitante(),
                    solicitud.getSonContacto(),
                    solicitud.getSonTelContacto(),
                    solicitud.getSonEstado(),
                    solicitud.getSonTipoSolucion(),
                    solicitud.getSonResGestion(),
                    solicitud.getSonCorregirHhpp(),
                    solicitud.getSonCambioNodo(),
                    solicitud.getSonNuevoProducto(),
                    solicitud.getSonEstratoAntiguo(),
                    solicitud.getSonEstratoNuevo(),
                    solicitud.getSonUsuarioCreacion(),
                    solicitud.getSonObservaciones(),
                    solicitud.getSonFormatoIgac(),
                    solicitud.getSonServinformacion(),
                    solicitud.getSonNostandar(),
                    solicitud.getSonTipoSolicitud(),
                    solicitud.getSonEstrato(),
                    solicitud.getSonNivSocioeconomico(),
                    solicitud.getSonZipcode(),
                    solicitud.getSonDiralterna(),
                    solicitud.getSonLocalidad(),
                    solicitud.getSonMz(),
                    solicitud.getSonLongitud(),
                    solicitud.getSonLatitud(),
                    solicitud.getSonLadomz(),
                    solicitud.getSonHeadEnd(),
                    solicitud.getSonCodSolicitante(),
                    solicitud.getSonEmailSolicitante(),
                    solicitud.getSonCelSolicitante(),
                    solicitud.getSonEstadoSol(),
                    solicitud.getSonEstadoUni(),
                    solicitud.getSonUsuGestion(),
                    solicitud.getSonCampoa1(),
                    solicitud.getSonCampoa2(),
                    solicitud.getSonCampoa3(),
                    solicitud.getSonCampoa4(),
                    solicitud.getSonCampoa5(),
                    solicitud.getSonDirExiste(),
                    solicitud.getSonDirValidada(),
                    solicitud.getSonNodoUsuario(),
                    confiabilidad,
                    solicitud.getSonManzanaCatastral(),
                    solicitud.getSonActividadEconomica(),
                    solicitud.getSonFuente(),
                    solicitud.getSonPlaca(),
                    solicitud.getSonComplemento(),
                    solicitud.getSonAreaSolicitante());

            idson = getIdSolicitudNegocio(adb);
            if (idson != null) {
                SolicitudNegocio solpersisted = querySolicitudNegocio(idson);
                EnvioCorreo envio = new EnvioCorreo();
                String mensaje = Constant.CREATE_SOLICITUD + ", con el ID:" + idson + ", para: " + solicitud.getSonNostandar() + ".";
                envio.envio(solpersisted.getSonEmailSolicitante(), Constant.CREATE_SOLICITUD, mensaje);
                AuditoriaEJB audi = new AuditoriaEJB();
                audi.auditar(nombreFuncionalidad, Constant.SOLICITUD_NEGOCIO, solpersisted.getSonUsuarioCreacion(), Constant.INSERT, solpersisted.auditoria(), adb);
            }
            DireccionUtil.closeConnection(adb);
            return idson;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al agregar la solicitud negocio. EX000 " + e.getMessage(), e);
        }
    }

    /**
     * Consultar las solicitudes pendientes por Gestionar
     *
     * @param estado Estado de las solicitudes a consultar
     * @param gpo Geografico politico al cual deben pertenecer las solicitades
     * @param tipoSolicitud Tipo de solicitudes a consultar.
     * @return Lista de solicitudes en estado 'estado' de tipo 'tipoSolicitud' y
     * para la ciudad 'gpo'
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public List<SolicitudNegocio> querySolicitudesPorGestionar(String estado, GeograficoPolitico gpo, String tipoSolicitud) throws ApplicationException {
        List<SolicitudNegocio> listsols = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("son6", gpo.getGpoId().toString(), estado, tipoSolicitud);
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                listsols = new ArrayList<SolicitudNegocio>();
                for (DataObject obj : list.getList()) {
                    BigDecimal son_id = obj.getBigDecimal("SON_ID");
                    String son_formato_IGAC = obj.getString("SON_FORMATO_IGAC");
                    String son_nostandar = obj.getString("SON_NOSTANDAR");
                    String son_fecha_creacion = obj.getString("FECHA_CREA");
                    SolicitudNegocio solicitud = new SolicitudNegocio();
                    solicitud.setSonId(son_id);
                    solicitud.setSonFormatoIgac(son_formato_IGAC);
                    solicitud.setSonNostandar(son_nostandar);
                    SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    Date fecha = null;
                    try {
                        fecha = formatoDelTexto.parse(son_fecha_creacion);
                    } catch (ParseException ex) {
                        LOGGER.error("Error el consultar solicitudes por gestionar. EX000 " + ex.getMessage(), ex);
                    }
                    solicitud.setSonFechaCreacion(fecha);
                    listsols.add(solicitud);
                }
            }
            return listsols;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar solicitudes por gestionar. EX000 " + e.getMessage(), e);
        }
    }

    /**
     * Cargar las solicitudes pendientes por procesa, es decir las solicitudes
     * que se encuentren en estado PROCESADA
     *
     * @param tipoSolicitud
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public List<SolicitudNegocio> querySolicitudesPorProcesar(String tipoSolicitud) throws ApplicationException {
        List<SolicitudNegocio> listsols = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("son12", tipoSolicitud);
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                listsols = new ArrayList<SolicitudNegocio>();
                for (DataObject obj : list.getList()) {
                    /*"SELECT SN.SON_ID,SN.GPO_ID,SN.GEO_ID,SN.THR_ID,SN.THC_ID,SN.THH_ID,SN.TMA_ID,
                     * N.NOD_ID,N.NOD_NOMBRE,N.NOD_CODIGO,N.NOD_HEAD_END,N.NOD_TIPO,N.COM_ID,*/
                    SolicitudNegocio solicitudN = new SolicitudNegocio();
                    ConsultaSolicitudEJB consultaEjb = new ConsultaSolicitudEJB();
                    HhppEJB hhppEjb = new HhppEJB();
                    //SN.SON_ID,SN.GPO_ID,N.NOD_ID,N.NOD_NOMBRE,N.NOD_CODIGO,N.NOD_HEAD_END,N.NOD_TIPO,N.COM_ID,SN.THH_ID,SN.GEO_ID,SN.THR_ID,SN.THC_ID,SN.TMA_ID,
                    solicitudN.setSonId(obj.getBigDecimal("SON_ID"));
                    BigDecimal id_gpo = obj.getBigDecimal("GEOGRAFICO_POLITICO_ID");
                    BigDecimal id_geo = obj.getBigDecimal("GEO_ID");
                    BigDecimal id_thr = obj.getBigDecimal("THR_ID");
                    BigDecimal id_thc = obj.getBigDecimal("THC_ID");
                    String id_thh = obj.getString("THH_ID");
                    BigDecimal id_tma = obj.getBigDecimal("TMA_ID");
                    BigDecimal id_hhp = obj.getBigDecimal("HHP_ID");
                    GeograficoPolitico gpo = null;
                    if (id_gpo != null) {
                        gpo = consultaEjb.queryGeograficoPolitico(id_gpo);
                    }
                    TipoHhpp thhpp = consultaEjb.queryTipoHhpp(id_thh);
                    Geografico geo = null;
                    if (id_geo != null) {
                        geo = nodoManager.queryGeografico(id_geo);
                    }
                    Hhpp hhpp = null;
                    if (id_hhp != null) {
                        hhpp = hhppEjb.queryHhppById(id_hhp.toString());
                    }
                    TipoHhppRed thr = null;
                    if (id_thr != null) {
                        thr = consultaEjb.queryTipoHhppRed(id_thr);
                    }
                    TipoHhppConexion thc = null;
                    if (id_thc != null) {
                        thc = consultaEjb.queryTipoHhppConexion(id_thc);
                    }
                    Marcas ma = null;
                    if (id_tma != null) {
                        ma = queryMarcas(id_tma);
                    }
                    solicitudN.setGeograficoPolitico(gpo);
                    solicitudN.setHhpp(hhpp);
                    solicitudN.setTipoHhpp(thhpp);
                    solicitudN.setGeografico(geo);
                    solicitudN.setTipoHhppRed(thr);
                    solicitudN.setTipoHhppConexion(thc);
                    solicitudN.setMarcas(ma);
                    Nodo nodo = new Nodo();
                    nodo.setNodId(obj.getBigDecimal("NODO_ID"));
                    nodo.setNodNombre(obj.getString("NOD_NOMBRE"));
                    nodo.setNodCodigo(obj.getString("NOD_CODIGO"));
                    nodo.setNodHeadend(obj.getString("NOD_HEAD_END"));
                    nodo.setNodTipo(obj.getBigDecimal("NOD_TIPO"));
                    Comunidad comunidad = new Comunidad();
                    comunidad.setComId(obj.getString("COM_ID"));
                    nodo.setComunidad(comunidad);
                    solicitudN.setNodo(nodo);
                    /* SN.SON_MOTIVO,SN.SON_NOM_SOLICITANTE,SN.SON_CONTACTO,SN.SON_TEL_CONTACTO,
                     * SN.SON_ESTADO,SN.SON_TIPO_SOLUCION,SN.SON_RES_GESTION,
                     * SN.SON_OBSERVACIONES,SN.SON_FORMATO_IGAC,SN.SON_SERVINFORMACION,SN.SON_NOSTANDAR,
                     * SN.SON_TIPO_SOLICITUD,SN.SON_ESTRATO,SN.SON_ZIPCODE,SN.SON_DIRALTERNA,
                     * SN.SON_LOCALIDAD,SN.SON_MZ,SN.SON_LONGITUD,SN.SON_LATITUD,SN.SON_LADOMZ,
                     */
                    solicitudN.setSonMotivo(obj.getString("SON_MOTIVO"));
                    solicitudN.setSonNomSolicitante(obj.getString("SON_NOM_SOLICITANTE"));
                    solicitudN.setSonContacto(obj.getString("SON_CONTACTO"));
                    solicitudN.setSonTelContacto(obj.getString("SON_TEL_CONTACTO"));
                    solicitudN.setSonEstado(obj.getString("SON_ESTADO"));
                    solicitudN.setSonTipoSolucion(obj.getString("SON_TIPO_SOLUCION"));
                    solicitudN.setSonResGestion(obj.getString("SON_RES_GESTION"));
                    solicitudN.setSonObservaciones(obj.getString("SON_OBSERVACIONES"));
                    solicitudN.setSonFormatoIgac(obj.getString("SON_FORMATO_IGAC"));
                    solicitudN.setSonServinformacion(obj.getString("SON_SERVINFORMACION"));
                    solicitudN.setSonNostandar(obj.getString("SON_NOSTANDAR"));
                    solicitudN.setSonTipoSolicitud(obj.getString("SON_TIPO_SOLICITUD"));
                    solicitudN.setSonEstrato(obj.getString("SON_ESTRATO"));
                    solicitudN.setSonZipcode(obj.getString("SON_ZIPCODE"));
                    solicitudN.setSonDiralterna(obj.getString("SON_DIRALTERNA"));
                    solicitudN.setSonLocalidad(obj.getString("SON_LOCALIDAD"));
                    solicitudN.setSonMz(obj.getString("SON_MZ"));
                    solicitudN.setSonLongitud(obj.getString("SON_LONGITUD"));
                    solicitudN.setSonLatitud(obj.getString("SON_LATITUD"));
                    solicitudN.setSonLadomz(obj.getString("SON_LADOMZ"));
                    solicitudN.setSonHeadEnd(obj.getString("SON_HEAD_END"));
                    solicitudN.setSonCodSolicitante(obj.getString("SON_COD_SOLICITANTE"));
                    solicitudN.setSonEmailSolicitante(obj.getString("SON_EMAIL_SOLICITANTE"));
                    solicitudN.setSonCelSolicitante(obj.getString("SON_CEL_SOLICITANTE"));
                    solicitudN.setSonEstadoUni(obj.getString("SON_ESTADO_UNI"));
                    solicitudN.setSonUsuGestion(obj.getString("SON_USU_GESTION"));
                    solicitudN.setSonCampoa1(obj.getString("SON_CAMPOA1"));
                    solicitudN.setSonCampoa2(obj.getString("SON_CAMPOA2"));
                    solicitudN.setSonCampoa3(obj.getString("SON_CAMPOA3"));
                    solicitudN.setSonCampoa4(obj.getString("SON_CAMPOA4"));
                    solicitudN.setSonCampoa5(obj.getString("SON_CAMPOA5"));
                    solicitudN.setSonDirExiste(obj.getString("SON_DIREXISTE"));
                    solicitudN.setSonDirValidada(obj.getString("SON_DIRVALIDADA"));
                    solicitudN.setSonNivSocioeconomico(obj.getString("SON_NIVSOCIOECONOMICO"));
                    solicitudN.setSonPlaca(obj.getString("SON_PLACA"));
                    solicitudN.setSonComplemento(obj.getString("SON_COMPLEMENTO"));
                    solicitudN.setSonArchivoGeneradoRr(obj.getString("SON_ARCHIVO_GENERADO_RR"));
                    solicitudN.setSonAreaSolicitante(obj.getString("SON_AREA_SOLICITANTE"));
                    solicitudN.setSonNodoUsuario(obj.getString("SON_NODOUSUARIO"));
                    solicitudN.setSonConfiabilidad(obj.getBigDecimal("SON_CONFIABILIDAD"));
                    solicitudN.setSonManzanaCatastral(obj.getString("SON_MANZANACATASTRAL"));
                    solicitudN.setSonActividadEconomica(obj.getString("SON_ACTIVIDADECONOMICA"));
                    solicitudN.setSonFuente(obj.getString("SON_FUENTE"));
                    listsols.add(solicitudN);
                }
            }
            return listsols;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error eal consultar solicitudes por procesar. EX000 " + e.getMessage(), e);
        }
    }

    /**
     * Consultar las solicitudes incluidas en el archivo nombreArchivo
     *
     * @param nombreArchivo Nombre de archivo de las solicitudes que se van a
     * crear
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public List<SolicitudNegocio> querySolicitudesParaRR(String nombreArchivo) throws ApplicationException {
        List<SolicitudNegocio> listsols = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("son13", nombreArchivo);
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                listsols = new ArrayList<SolicitudNegocio>();
                for (DataObject obj : list.getList()) {
                    /*"SELECT SN.SON_ID,SN.GPO_ID,SN.GEO_ID,SN.THR_ID,SN.THC_ID,SN.THH_ID,SN.TMA_ID,
                     * N.NOD_ID,N.NOD_NOMBRE,N.NOD_CODIGO,N.NOD_HEAD_END,N.NOD_TIPO,N.COM_ID,*/
                    SolicitudNegocio solicitudN = new SolicitudNegocio();
                    ConsultaSolicitudEJB consultaEjb = new ConsultaSolicitudEJB();
                    HhppEJB hhppEjb = new HhppEJB();
                    //SN.SON_ID,SN.GPO_ID,N.NOD_ID,N.NOD_NOMBRE,N.NOD_CODIGO,N.NOD_HEAD_END,N.NOD_TIPO,N.COM_ID,SN.THH_ID,SN.GEO_ID,SN.THR_ID,SN.THC_ID,SN.TMA_ID,
                    solicitudN.setSonId(obj.getBigDecimal("SON_ID"));
                    BigDecimal id_gpo = obj.getBigDecimal("GEOGRAFICO_POLITICO_ID");
                    BigDecimal id_geo = obj.getBigDecimal("GEO_ID");
                    BigDecimal id_thr = obj.getBigDecimal("THR_ID");
                    BigDecimal id_thc = obj.getBigDecimal("THC_ID");
                    String id_thh = obj.getString("THH_ID");
                    BigDecimal id_tma = obj.getBigDecimal("TMA_ID");
                    BigDecimal id_hhpp = obj.getBigDecimal("HHP_ID");
                    GeograficoPolitico gpo = null;
                    if (id_gpo != null) {
                        gpo = consultaEjb.queryGeograficoPolitico(id_gpo);
                    }
                    TipoHhpp thhpp = consultaEjb.queryTipoHhpp(id_thh);
                    Geografico geo = null;
                    if (id_geo != null) {
                        geo = nodoManager.queryGeografico(id_geo);
                    }
                    TipoHhppRed thr = null;
                    if (id_thr != null) {
                        thr = consultaEjb.queryTipoHhppRed(id_thr);
                    }
                    TipoHhppConexion thc = null;
                    if (id_thc != null) {
                        thc = consultaEjb.queryTipoHhppConexion(id_thc);
                    }
                    Marcas ma = null;
                    if (id_tma != null) {
                        ma = queryMarcas(id_tma);
                    }
                    Hhpp hhpp = null;
                    if (id_hhpp != null) {
                        hhpp = hhppEjb.queryHhppById(id_hhpp.toString());
                    }
                    solicitudN.setGeograficoPolitico(gpo);
                    solicitudN.setTipoHhpp(thhpp);
                    solicitudN.setGeografico(geo);
                    solicitudN.setTipoHhppRed(thr);
                    solicitudN.setTipoHhppConexion(thc);
                    solicitudN.setMarcas(ma);
                    solicitudN.setHhpp(hhpp);
                    Nodo nodo = new Nodo();
                    nodo.setNodId(obj.getBigDecimal("NODO_ID"));
                    nodo.setNodNombre(obj.getString("NOD_NOMBRE"));
                    nodo.setNodCodigo(obj.getString("NOD_CODIGO"));
                    nodo.setNodHeadend(obj.getString("NOD_HEAD_END"));
                    nodo.setNodTipo(obj.getBigDecimal("NOD_TIPO"));
                    String id_com = obj.getString("COM_ID");
                    BigDecimal id_div = obj.getBigDecimal("DIV_ID");
                    BigDecimal id_nodGpo = obj.getBigDecimal("NGPO_ID");

                    Comunidad comunidad = new Comunidad();
                    if (id_com != null) {
                        comunidad = consultaEjb.queryComunidad(id_com);
                    }
                    Divisional divisional = new Divisional();
                    divisional.setDivId(id_div);

                    GeograficoPolitico geoPoliticoNodo = new GeograficoPolitico();
                    if (id_nodGpo != null) {
                        geoPoliticoNodo = consultaEjb.queryGeograficoPolitico(id_nodGpo);
                    }

                    nodo.setComunidad(comunidad);
                    nodo.setDivisional(divisional);
                    nodo.setGeograficoPolitico(geoPoliticoNodo);
                    solicitudN.setNodo(nodo);
                    /* SN.SON_MOTIVO,SN.SON_NOM_SOLICITANTE,SN.SON_CONTACTO,SN.SON_TEL_CONTACTO,
                     * SN.SON_ESTADO,SN.SON_TIPO_SOLUCION,SN.SON_RES_GESTION,
                     * SN.SON_OBSERVACIONES,SN.SON_FORMATO_IGAC,SN.SON_SERVINFORMACION,SN.SON_NOSTANDAR,
                     * SN.SON_TIPO_SOLICITUD,SN.SON_ESTRATO,SN.SON_ZIPCODE,SN.SON_DIRALTERNA,
                     * SN.SON_LOCALIDAD,SN.SON_MZ,SN.SON_LONGITUD,SN.SON_LATITUD,SN.SON_LADOMZ,
                     */
                    solicitudN.setSonMotivo(obj.getString("SON_MOTIVO"));
                    solicitudN.setSonNomSolicitante(obj.getString("SON_NOM_SOLICITANTE"));
                    solicitudN.setSonContacto(obj.getString("SON_CONTACTO"));
                    solicitudN.setSonTelContacto(obj.getString("SON_TEL_CONTACTO"));
                    solicitudN.setSonEstado(obj.getString("SON_ESTADO"));
                    solicitudN.setSonTipoSolucion(obj.getString("SON_TIPO_SOLUCION"));
                    solicitudN.setSonResGestion(obj.getString("SON_RES_GESTION"));
                    solicitudN.setSonObservaciones(obj.getString("SON_OBSERVACIONES"));
                    solicitudN.setSonFormatoIgac(obj.getString("SON_FORMATO_IGAC"));
                    solicitudN.setSonServinformacion(obj.getString("SON_SERVINFORMACION"));
                    solicitudN.setSonNostandar(obj.getString("SON_NOSTANDAR"));
                    solicitudN.setSonTipoSolicitud(obj.getString("SON_TIPO_SOLICITUD"));
                    solicitudN.setSonEstrato(obj.getString("SON_ESTRATO"));
                    solicitudN.setSonZipcode(obj.getString("SON_ZIPCODE"));
                    solicitudN.setSonDiralterna(obj.getString("SON_DIRALTERNA"));
                    solicitudN.setSonLocalidad(obj.getString("SON_LOCALIDAD"));
                    solicitudN.setSonMz(obj.getString("SON_MZ"));
                    solicitudN.setSonLongitud(obj.getString("SON_LONGITUD"));
                    solicitudN.setSonLatitud(obj.getString("SON_LATITUD"));
                    solicitudN.setSonLadomz(obj.getString("SON_LADOMZ"));
                    solicitudN.setSonHeadEnd(obj.getString("SON_HEAD_END"));
                    solicitudN.setSonCodSolicitante(obj.getString("SON_COD_SOLICITANTE"));
                    solicitudN.setSonEmailSolicitante(obj.getString("SON_EMAIL_SOLICITANTE"));
                    solicitudN.setSonCelSolicitante(obj.getString("SON_CEL_SOLICITANTE"));
                    solicitudN.setSonEstadoUni(obj.getString("SON_ESTADO_UNI"));
                    solicitudN.setSonUsuGestion(obj.getString("SON_USU_GESTION"));
                    solicitudN.setSonCampoa1(obj.getString("SON_CAMPOA1"));
                    solicitudN.setSonCampoa2(obj.getString("SON_CAMPOA2"));
                    solicitudN.setSonCampoa3(obj.getString("SON_CAMPOA3"));
                    solicitudN.setSonCampoa4(obj.getString("SON_CAMPOA4"));
                    solicitudN.setSonCampoa5(obj.getString("SON_CAMPOA5"));
                    solicitudN.setSonDirExiste(obj.getString("SON_DIREXISTE"));
                    solicitudN.setSonDirValidada(obj.getString("SON_DIRVALIDADA"));
                    solicitudN.setSonNivSocioeconomico(obj.getString("SON_NIVSOCIOECONOMICO"));
                    solicitudN.setSonPlaca(obj.getString("SON_PLACA"));
                    solicitudN.setSonComplemento(obj.getString("SON_COMPLEMENTO"));
                    solicitudN.setSonArchivoGeneradoRr(obj.getString("SON_ARCHIVO_GENERADO_RR"));
                    solicitudN.setSonAreaSolicitante(obj.getString("SON_AREA_SOLICITANTE"));
                    solicitudN.setSonNodoUsuario(obj.getString("SON_NODOUSUARIO"));
                    solicitudN.setSonConfiabilidad(obj.getBigDecimal("SON_CONFIABILIDAD"));
                    solicitudN.setSonManzanaCatastral(obj.getString("SON_MANZANACATASTRAL"));
                    solicitudN.setSonActividadEconomica(obj.getString("SON_ACTIVIDADECONOMICA"));
                    solicitudN.setSonFuente(obj.getString("SON_FUENTE"));
                    listsols.add(solicitudN);
                }
            }
            return listsols;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar solicitudes para RR. EX000 " + e.getMessage(), e);
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
    public Nodo queryNodoNFI(BigDecimal gpo_id) throws ApplicationException {
        Nodo nodo = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("nod3", "%" + Constant.NODO_NFI + "%", gpo_id.toString());
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                SolicitudRedEJB solRed = new SolicitudRedEJB();
                ConsultaSolicitudEJB consultaEJB = new ConsultaSolicitudEJB();
                nodo = new Nodo();
                nodo.setNodId(obj.getBigDecimal("NODO_ID"));
                BigDecimal gpo_idN = obj.getBigDecimal("GEOGRAFICO_POLITICO_ID");
                if (gpo_idN != null) {
                    GeograficoPolitico geograficoPo = new GeograficoPolitico();
                    geograficoPo = solRed.queryGeograficoPoliticoId(gpo_idN);
                    nodo.setGeograficoPolitico(geograficoPo);
                }
                nodo.setNodNombre(obj.getString("NOD_NOMBRE"));
                nodo.setNodCodigo(obj.getString("NOD_CODIGO"));
                BigDecimal uge_id = obj.getBigDecimal("UGE_ID");
                if (uge_id != null) {
                    UnidadGestion ugestion = new UnidadGestion();
                    ugestion.setUgeId(uge_id);
                    nodo.setUnidadGestion(ugestion);
                }
                BigDecimal zon_id = obj.getBigDecimal("ZON_ID");
                if (zon_id != null) {
                    Zona zona = new Zona();
                    zona.setZonId(zon_id);
                    nodo.setZona(zona);
                }
                String dis_id = obj.getString("DIS_ID");
                if (dis_id != null) {
                    Distrito distrito = new Distrito();
                    distrito.setDisId(dis_id);
                    nodo.setDistrito(distrito);
                }
                BigDecimal div_id = obj.getBigDecimal("DIV_ID");
                if (div_id != null) {
                    Divisional divisional = new Divisional();
                    divisional.setDivId(div_id);
                    nodo.setDivisional(divisional);
                }
                BigDecimal are_id = obj.getBigDecimal("ARE_ID");
                if (are_id != null) {
                    Area area = new Area();
                    area.setAreId(are_id);
                    nodo.setArea(area);
                }
                String nod_headEnd = obj.getString("NOD_HEAD_END");
                nodo.setNodHeadend(nod_headEnd);
                BigDecimal nod_Tipo = obj.getBigDecimal("NOD_TIPO");
                nodo.setNodTipo(nod_Tipo);
                String id_com = obj.getString("COM_ID");
                Comunidad comunidad = null;
                if (id_com != null) {
                    comunidad = consultaEJB.queryComunidad(id_com);
                    nodo.setComunidad(comunidad);
                }
            }
            return nodo;
        } catch (ApplicationException | ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar nodo NFI. EX000 " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param adb
     * @return
     * @throws Exception
     */
    private BigDecimal getIdSolicitudNegocio(AccessData adb) throws ApplicationException {
        try {
            BigDecimal id = null;
            DataObject obj = adb.outDataObjec("son2");
            if (obj != null) {
                id = obj.getBigDecimal("ID");
            }
            return id;
        } catch (ExceptionDB ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al traer el id de la solicitud de negocio. EX000 " + ex.getMessage(), ex);
        }
    }

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    @Override
    public List<Multivalor> loadTiposSolicitud() throws ApplicationException {
        List<Multivalor> tiposSolicitud = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("mul7");
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                tiposSolicitud = new ArrayList<Multivalor>();
                for (DataObject obj : list.getList()) {
                    BigDecimal mul_id = obj.getBigDecimal("MUL_ID");
                    String valor = obj.getString("MUL_VALOR");
                    String descripcion = obj.getString("MUL_DESCRIPCION");
                    Multivalor tsol = new Multivalor();
                    tsol.setMulId(mul_id);
                    tsol.setMulValor(valor);
                    tsol.setDescripcion(descripcion);
                    tiposSolicitud.add(tsol);
                }
            }
            return tiposSolicitud;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al cargar tipos solicitud. EX000 " + e.getMessage(), e);
        }
    }

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    @Override
    public List<Multivalor> loadTiposSolicitudModificacion() throws ApplicationException {
        List<Multivalor> tiposSolicitud = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("mul11", "18");
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                tiposSolicitud = new ArrayList<Multivalor>();
                for (DataObject obj : list.getList()) {
                    BigDecimal mul_id = obj.getBigDecimal("MUL_ID");
                    String valor = obj.getString("MUL_VALOR");
                    String descripcion = obj.getString("MUL_DESCRIPCION");
                    Multivalor tsol = new Multivalor();
                    tsol.setMulId(mul_id);
                    tsol.setMulValor(valor);
                    tsol.setDescripcion(descripcion);
                    tiposSolicitud.add(tsol);
                }
            }
            return tiposSolicitud;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al cargar tipos solicitud modificacion. EX000 " + e.getMessage(), e);
        }
    }

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    @Override
    public List<Multivalor> loadTiposRespuesta() throws ApplicationException {
        List<Multivalor> tiposRespuesta = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("mul11", "14");
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                tiposRespuesta = new ArrayList<Multivalor>();
                for (DataObject obj : list.getList()) {
                    BigDecimal mul_id = obj.getBigDecimal("MUL_ID");
                    String valor = obj.getString("MUL_VALOR");
                    String descripcion = obj.getString("MUL_DESCRIPCION");
                    Multivalor tres = new Multivalor();
                    tres.setMulId(mul_id);
                    tres.setMulValor(valor);
                    tres.setDescripcion(descripcion);
                    tiposRespuesta.add(tres);
                }
            }
            return tiposRespuesta;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al cargar tipos de respuesta. EX000 " + e.getMessage(), e);
        }
    }

    /**
     * Cargar Tipos de Respuesta permitidos en la gestion de Solicitud de
     * Modificación de Hhpp
     *
     * @return Respuestas permitidas en la gestion de Solicitud de Modificación
     * de Hhpp
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public List<Multivalor> loadTiposRespuestaModificacion() throws ApplicationException {
        List<Multivalor> tiposRespuesta = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("mul11", "19");
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                tiposRespuesta = new ArrayList<Multivalor>();
                for (DataObject obj : list.getList()) {
                    BigDecimal mul_id = obj.getBigDecimal("MUL_ID");
                    String valor = obj.getString("MUL_VALOR");
                    String descripcion = obj.getString("MUL_DESCRIPCION");
                    Multivalor tres = new Multivalor();
                    tres.setMulId(mul_id);
                    tres.setMulValor(valor);
                    tres.setDescripcion(descripcion);
                    tiposRespuesta.add(tres);
                }
            }
            return tiposRespuesta;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al cargar tipos de respuesta modificación. EX000 " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param idGrupoMultivalor
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public List<Multivalor> loadMultivalores(String idGrupoMultivalor) throws ApplicationException {
        List<Multivalor> multivalores = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("mul14", idGrupoMultivalor);
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                multivalores = new ArrayList<Multivalor>();
                for (DataObject obj : list.getList()) {
                    BigDecimal mul_id = obj.getBigDecimal("MUL_ID");
                    String valor = obj.getString("MUL_VALOR");
                    String descripcion = obj.getString("MUL_DESCRIPCION");
                    Multivalor mul = new Multivalor();
                    mul.setMulId(mul_id);
                    mul.setMulValor(valor);
                    mul.setDescripcion(descripcion);
                    multivalores.add(mul);
                }
            }
            return multivalores;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al cargar multivalores. EX000 " + e.getMessage(), e);
        }
    }

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    @Override
    public List<GeograficoPolitico> queryPaises() throws ApplicationException {
        List<GeograficoPolitico> paises = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("gpo5");
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                paises = new ArrayList<GeograficoPolitico>();
                for (DataObject obj : list.getList()) {
                    BigDecimal gpo_id = obj.getBigDecimal("GEOGRAFICO_POLITICO_ID");
                    String name = obj.getString("GPO_NOMBRE");
                    GeograficoPolitico pais = new GeograficoPolitico();
                    pais.setGpoId(gpo_id);
                    pais.setGpoNombre(name);
                    paises.add(pais);
                }
            }
            return paises;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar paises. EX000 " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param idPais
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public List<GeograficoPolitico> queryRegionales(BigDecimal idPais) throws ApplicationException {
        List<GeograficoPolitico> regionales = null;
        ConsultaSolicitudEJB consultEJB = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("gpo4", idPais.toString());
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                regionales = new ArrayList<GeograficoPolitico>();
                consultEJB = new ConsultaSolicitudEJB();
                for (DataObject obj : list.getList()) {
                    BigDecimal gpo_id = obj.getBigDecimal("GPO_IDGEOGRAFICO_POLITICO_ID");
                    BigDecimal geo_gpo_id = obj.getBigDecimal("GEO_GPO_ID");
                    String name = obj.getString("GPO_NOMBRE");
                    GeograficoPolitico region = new GeograficoPolitico();
                    region.setGpoId(gpo_id);
                    GeograficoPolitico pais = new GeograficoPolitico();
                    pais = consultEJB.queryGeograficoPolitico(geo_gpo_id);
                    region.setGeograficoPolitico(pais);
                    region.setGpoNombre(name);
                    regionales.add(region);
                }
            }
            return regionales;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar regionales. EX000 " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param idRegional
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public List<GeograficoPolitico> queryCiudades(BigDecimal idRegional) throws ApplicationException {
        List<GeograficoPolitico> ciudades = null;
        ConsultaSolicitudEJB consultaEjb = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("gpo3", idRegional.toString());
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                ciudades = new ArrayList<GeograficoPolitico>();
                consultaEjb = new ConsultaSolicitudEJB();
                for (DataObject obj : list.getList()) {
                    BigDecimal gpo_id = obj.getBigDecimal("GEOGRAFICO_POLITICO_ID");
                    BigDecimal geo_gpo_id = obj.getBigDecimal("GEO_GPO_ID");
                    String name = obj.getString("GPO_NOMBRE");
                    String multiorigen = obj.getString("GPO_MULTIORIGEN");
                    String codTipoDir = obj.getString("GPO_COD_TIPO_DIRECCION");
                    GeograficoPolitico ciudad = new GeograficoPolitico();
                    ciudad.setGpoId(gpo_id);
                    GeograficoPolitico pais = new GeograficoPolitico();
                    pais = consultaEjb.queryGeograficoPolitico(geo_gpo_id);
                    ciudad.setGeograficoPolitico(pais);
                    ciudad.setGpoNombre(name);
                    ciudad.setGpoMultiorigen(multiorigen);
                    ciudad.setGpoCodTipoDireccion(codTipoDir);
                    ciudades.add(ciudad);
                }
            }
            return ciudades;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar ciudades. EX000 " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param neighborhoodName
     * @param gpo_id
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public Geografico queryNeighborhood(String neighborhoodName, BigDecimal gpo_id) throws ApplicationException {
        Geografico geografico = null;
        AccessData adb = null;
        try {
            ConsultaSolicitudEJB consultaEjb = new ConsultaSolicitudEJB();
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("geo2", "%" + neighborhoodName + "%", gpo_id.toString());
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                geografico = new Geografico();
                consultaEjb = new ConsultaSolicitudEJB();
                geografico.setGeoId(obj.getBigDecimal("GEO_ID"));
                geografico.setGeoNombre(neighborhoodName);
                GeograficoPolitico gpo = consultaEjb.queryGeograficoPolitico(gpo_id);
                geografico.setGeograficoPolitico(gpo);
                BigDecimal tge_id = obj.getBigDecimal("TGE_ID");
                SolicitudRedEJB srEJB = new SolicitudRedEJB();

                BigDecimal geo_geo_id = obj.getBigDecimal("GEO_GEO_ID");
                if (geo_geo_id != null) {
                    Geografico geo_geo = srEJB.queryGeografico(geo_geo_id);
                    geografico.setGeografico(geo_geo);
                }
            }
            return geografico;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar neighborhood. EX000 " + e.getMessage(), e);
        }
    }

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    @Override
    public List<Multivalor> queryCalles() throws ApplicationException {
        List<Multivalor> tiposCalles = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("mul9");
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                tiposCalles = new ArrayList<Multivalor>();
                for (DataObject obj : list.getList()) {
                    BigDecimal mul_id = obj.getBigDecimal("MUL_ID");
                    String valor = obj.getString("MUL_VALOR");
                    String descripcion = obj.getString("MUL_DESCRIPCION");
                    Multivalor tcalle = new Multivalor();
                    tcalle.setMulId(mul_id);
                    tcalle.setMulValor(valor);
                    tcalle.setDescripcion(descripcion);
                    tiposCalles.add(tcalle);
                }
            }
            return tiposCalles;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar calles EX000 " + e.getMessage(), e);
        }
    }

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    @Override
    public List<Multivalor> queryCardinales() throws ApplicationException {
        List<Multivalor> cardinales = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("mul8");
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                cardinales = new ArrayList<Multivalor>();
                for (DataObject obj : list.getList()) {
                    BigDecimal mul_id = obj.getBigDecimal("MUL_ID");
                    String valor = obj.getString("MUL_VALOR");
                    String descripcion = obj.getString("MUL_DESCRIPCION");
                    Multivalor cardinal = new Multivalor();
                    cardinal.setMulId(mul_id);
                    cardinal.setMulValor(valor);
                    cardinal.setDescripcion(descripcion);
                    cardinales.add(cardinal);
                }
            }
            return cardinales;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar cardinales. EX000 " + e.getMessage(), e);
        }
    }

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    @Override
    public List<Multivalor> queryLetras() throws ApplicationException {
        int bool = 0;
        List<Multivalor> letras = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("mul10");
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                letras = new ArrayList<Multivalor>();
                for (DataObject obj : list.getList()) {
                    BigDecimal mul_id = obj.getBigDecimal("MUL_ID");
                    String valor = obj.getString("MUL_VALOR");
                    String descripcion = obj.getString("MUL_DESCRIPCION");
                    try {
                        bool = Integer.parseInt(valor);
                    } catch (NumberFormatException e) {
                        bool = 0;
                    }
                    if (bool == 0) {
                        Multivalor letra = new Multivalor();
                        letra.setMulId(mul_id);
                        letra.setMulValor(valor);
                        letra.setDescripcion(descripcion);
                        letras.add(letra);
                    }
                }
            }
            return letras;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar letras. EX000 " + e.getMessage(), e);
        }
    }

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    @Override
    public List<Multivalor> queryLetrasyNumeros() throws ApplicationException {
        List<Multivalor> letras = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("mul10");
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                letras = new ArrayList<Multivalor>();
                for (DataObject obj : list.getList()) {
                    BigDecimal mul_id = obj.getBigDecimal("MUL_ID");
                    String valor = obj.getString("MUL_VALOR");
                    String descripcion = obj.getString("MUL_DESCRIPCION");
                    Multivalor letra = new Multivalor();
                    letra.setMulId(mul_id);
                    letra.setMulValor(valor);
                    letra.setDescripcion(descripcion);
                    letras.add(letra);
                }
            }
            return letras;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar letras y numeros. EX000 " + e.getMessage(), e);
        }
    }

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    @Override
    public List<Multivalor> queryEstrato() throws ApplicationException {
        List<Multivalor> estratos = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("mul11", "13");
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                estratos = new ArrayList<Multivalor>();
                for (DataObject obj : list.getList()) {
                    BigDecimal mul_id = obj.getBigDecimal("MUL_ID");
                    String valor = obj.getString("MUL_VALOR");
                    String descripcion = obj.getString("MUL_DESCRIPCION");
                    Multivalor estrato = new Multivalor();
                    estrato.setMulId(mul_id);
                    estrato.setMulValor(valor);
                    estrato.setDescripcion(descripcion);
                    estratos.add(estrato);
                }
            }
            return estratos;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar estrato. EX000 " + e.getMessage(), e);
        }
    }

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    @Override
    public List<Multivalor> queryPrefijos() throws ApplicationException {
        List<Multivalor> prefijos = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("mul11", "11");
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                prefijos = new ArrayList<Multivalor>();
                for (DataObject obj : list.getList()) {
                    BigDecimal mul_id = obj.getBigDecimal("MUL_ID");
                    String valor = obj.getString("MUL_VALOR");
                    String descripcion = obj.getString("MUL_DESCRIPCION");
                    Multivalor prefijo = new Multivalor();
                    prefijo.setMulId(mul_id);
                    prefijo.setMulValor(valor);
                    prefijo.setDescripcion(descripcion);
                    prefijos.add(prefijo);
                }
            }
            return prefijos;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar prefijos. EX000 " + e.getMessage(), e);
        }
    }

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    @Override
    public List<TipoHhpp> queryTipoHhpp() throws ApplicationException {
        List<TipoHhpp> tiposHhpp = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("thh1");
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                tiposHhpp = new ArrayList<TipoHhpp>();
                for (DataObject obj : list.getList()) {
                    String thh_id = obj.getString("THH_ID");
                    String valor = obj.getString("THH_VALOR");
                    TipoHhpp thh = new TipoHhpp();
                    thh.setThhId(thh_id);
                    thh.setThhValor(valor);
                    tiposHhpp.add(thh);
                }
            }
            return tiposHhpp;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar tipo HHPP. EX000 " + e.getMessage(), e);
        }
    }

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    @Override
    public List<TipoHhppConexion> queryTipoConexion() throws ApplicationException {
        List<TipoHhppConexion> tiposConexion = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("thc1");
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                tiposConexion = new ArrayList<TipoHhppConexion>();
                for (DataObject obj : list.getList()) {
                    BigDecimal thc_id = obj.getBigDecimal("THC_ID");
                    String codigo = obj.getString("THC_CODIGO");
                    String nombre = obj.getString("THC_NOMBRE");
                    TipoHhppConexion thc = new TipoHhppConexion();
                    thc.setThcId(thc_id);
                    thc.setThcCodigo(codigo);
                    thc.setThcNombre(nombre);
                    tiposConexion.add(thc);
                }
            }
            return tiposConexion;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar tipo de conexión. EX000 " + e.getMessage(), e);
        }
    }

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    @Override
    public List<TipoHhppRed> queryTipoRed() throws ApplicationException {
        List<TipoHhppRed> tiposRed = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("thr1");
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                tiposRed = new ArrayList<TipoHhppRed>();
                for (DataObject obj : list.getList()) {
                    BigDecimal thr_id = obj.getBigDecimal("THR_ID");
                    String codigo = obj.getString("THR_CODIGO");
                    String nombre = obj.getString("THR_NOMBRE");
                    TipoHhppRed thr = new TipoHhppRed();
                    thr.setThrId(thr_id);
                    thr.setThrCodigo(codigo);
                    thr.setThrNombre(nombre);
                    tiposRed.add(thr);
                }
            }
            return tiposRed;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar tipo de red. EX000 " + e.getMessage(), e);
        }
    }

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    @Override
    public List<Marcas> queryBlackList() throws ApplicationException {
        List<Marcas> blackL = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("mar1", Constant.TIPO_MARCA_BLACK);
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                blackL = new ArrayList<Marcas>();
                for (DataObject obj : list.getList()) {
                    BigDecimal mar_id = obj.getBigDecimal("MAR_ID");
                    String nombre = obj.getString("MAR_NOMBRE");
                    Marcas bl = new Marcas();
                    bl.setMarId(mar_id);
                    bl.setMarNombre(nombre);
                    blackL.add(bl);
                }
            }
            return blackL;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar el black list. EX000 " + e.getMessage(), e);
        }
    }

    //Camilo Gaviria - Consulta tipo de dirección por codigo DANE
    /**
     *
     * @param CodDane
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public String ConsultaTipoDireccionByCodigoDane(String CodDane) throws ApplicationException {
        String Resultado = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("city1", CodDane);
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                for (DataObject obj : list.getList()) {
                    Resultado = obj.getString("gpo_cod_tipo_direccion");
                }
            }
            return Resultado;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar tipo de dirección por codigo dane. EX000 " + e.getMessage(), e);
        }
    }
    // Fin consulta codigo dane

    /**
     * @param idMarcas
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public Marcas queryMarcas(BigDecimal idMarcas) throws ApplicationException {
        Marcas marca = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("mar3", idMarcas.toString());
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                marca = new Marcas();
                BigDecimal mar_id = obj.getBigDecimal("MAR_ID");
                String nombre = obj.getString("MAR_NOMBRE");
                BigDecimal tma_id = obj.getBigDecimal("TMA_ID");
                String tma_nombre = obj.getString("TMA_NOMBRE");
                String tma_codigo = obj.getString("TMA_CODIGO");
                TipoMarcas tma = new TipoMarcas();
                marca.setMarId(mar_id);
                marca.setMarNombre(nombre);
                tma.setTmaId(tma_id);
                tma.setTmaCodigo(tma_codigo);
                tma.setTmaNombre(tma_nombre);
                marca.setTipoMarca(tma);
            }
            return marca;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar marcas. EX000 " + e.getMessage(), e);
        }
    }

    /**
     * Consultar una solicitud de negocio
     *
     * @param idSolicitud
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public SolicitudNegocio querySolicitudNegocio(BigDecimal idSolicitud) throws ApplicationException {
        SolicitudNegocio solicitudN = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("son3", idSolicitud.toString());
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                solicitudN = new SolicitudNegocio();
                ConsultaSolicitudEJB consultaEjb = new ConsultaSolicitudEJB();
                //SN.SON_ID,SN.GPO_ID,N.NOD_ID,N.NOD_NOMBRE,N.NOD_CODIGO,N.NOD_HEAD_END,N.NOD_TIPO,N.COM_ID,SN.THH_ID,SN.GEO_ID,SN.THR_ID,SN.THC_ID,SN.TMA_ID,
                solicitudN.setSonId(obj.getBigDecimal("SON_ID"));
                BigDecimal id_gpo = obj.getBigDecimal("GEOGRAFICO_POLITICO_ID");
                BigDecimal id_geo = obj.getBigDecimal("GEO_ID");
                BigDecimal id_thr = obj.getBigDecimal("THR_ID");
                BigDecimal id_thc = obj.getBigDecimal("THC_ID");
                String id_thh = obj.getString("THH_ID");
                BigDecimal id_tma = obj.getBigDecimal("TMA_ID");
                GeograficoPolitico gpo = null;
                if (id_gpo != null) {
                    gpo = consultaEjb.queryGeograficoPolitico(id_gpo);
                }
                TipoHhpp thhpp = consultaEjb.queryTipoHhpp(id_thh);
                Geografico geo = null;
                if (id_geo != null) {
                    geo = nodoManager.queryGeografico(id_geo);
                }
                TipoHhppRed thr = null;
                if (id_thr != null) {
                    thr = consultaEjb.queryTipoHhppRed(id_thr);
                }
                TipoHhppConexion thc = null;
                if (id_thc != null) {
                    thc = consultaEjb.queryTipoHhppConexion(id_thc);
                }
                Marcas ma = null;
                if (id_tma != null) {
                    ma = queryMarcas(id_tma);
                }
                solicitudN.setGeograficoPolitico(gpo);
                solicitudN.setTipoHhpp(thhpp);
                solicitudN.setGeografico(geo);
                solicitudN.setTipoHhppRed(thr);
                solicitudN.setTipoHhppConexion(thc);
                solicitudN.setMarcas(ma);
                Nodo nodo = new Nodo();
                nodo.setNodId(obj.getBigDecimal("NODO_ID"));
                nodo.setNodNombre(obj.getString("NOD_NOMBRE"));
                nodo.setNodCodigo(obj.getString("NOD_CODIGO"));
                nodo.setNodHeadend(obj.getString("NOD_HEAD_END"));
                nodo.setNodTipo(obj.getBigDecimal("NOD_TIPO"));
                Comunidad comunidad = new Comunidad();
                comunidad.setComId(obj.getString("COM_ID"));
                nodo.setComunidad(comunidad);
                solicitudN.setNodo(nodo);
                solicitudN.setSonMotivo(obj.getString("SON_MOTIVO"));
                solicitudN.setSonNomSolicitante(obj.getString("SON_NOM_SOLICITANTE"));
                solicitudN.setSonContacto(obj.getString("SON_CONTACTO"));
                solicitudN.setSonTelContacto(obj.getString("SON_TEL_CONTACTO"));
                solicitudN.setSonEstado(obj.getString("SON_ESTADO"));
                solicitudN.setSonTipoSolucion(obj.getString("SON_TIPO_SOLUCION"));
                solicitudN.setSonResGestion(obj.getString("SON_RES_GESTION"));
                solicitudN.setSonObservaciones(obj.getString("SON_OBSERVACIONES"));
                solicitudN.setSonFormatoIgac(obj.getString("SON_FORMATO_IGAC"));
                solicitudN.setSonDiralterna(obj.getString("SON_SERVINFORMACION"));
                solicitudN.setSonNostandar(obj.getString("SON_NOSTANDAR"));
                solicitudN.setSonTipoSolicitud(obj.getString("SON_TIPO_SOLICITUD"));
                solicitudN.setSonEstrato(obj.getString("SON_ESTRATO"));
                solicitudN.setSonZipcode(obj.getString("SON_ZIPCODE"));
                solicitudN.setSonDiralterna(obj.getString("SON_DIRALTERNA"));
                solicitudN.setSonLocalidad(obj.getString("SON_LOCALIDAD"));
                solicitudN.setSonMz(obj.getString("SON_MZ"));
                solicitudN.setSonLongitud(obj.getString("SON_LONGITUD"));
                solicitudN.setSonLatitud(obj.getString("SON_LATITUD"));
                solicitudN.setSonLadomz(obj.getString("SON_LADOMZ"));
                solicitudN.setSonHeadEnd(obj.getString("SON_HEAD_END"));
                solicitudN.setSonCodSolicitante(obj.getString("SON_COD_SOLICITANTE"));
                solicitudN.setSonEmailSolicitante(obj.getString("SON_EMAIL_SOLICITANTE"));
                solicitudN.setSonCelSolicitante(obj.getString("SON_CEL_SOLICITANTE"));
                solicitudN.setSonCelSolicitante(obj.getString("SON_CEL_SOLICITANTE"));
                solicitudN.setSonEstadoUni(obj.getString("SON_ESTADO_UNI"));
                solicitudN.setSonUsuGestion(obj.getString("SON_USU_GESTION"));
                solicitudN.setSonCampoa1(obj.getString("SON_CAMPOA1"));
                solicitudN.setSonCampoa2(obj.getString("SON_CAMPOA2"));
                solicitudN.setSonCampoa3(obj.getString("SON_CAMPOA3"));
                solicitudN.setSonCampoa4(obj.getString("SON_CAMPOA4"));
                solicitudN.setSonCampoa5(obj.getString("SON_CAMPOA5"));
                solicitudN.setSonDirExiste(obj.getString("SON_DIREXISTE"));
                solicitudN.setSonDirValidada(obj.getString("SON_DIRVALIDADA"));
                solicitudN.setSonNivSocioeconomico(obj.getString("SON_NIVSOCIOECONOMICO"));
                solicitudN.setSonPlaca(obj.getString("SON_PLACA"));
                solicitudN.setSonComplemento(obj.getString("SON_COMPLEMENTO"));
                solicitudN.setSonArchivoGeneradoRr(obj.getString("SON_ARCHIVO_GENERADO_RR"));
                solicitudN.setSonAreaSolicitante(obj.getString("SON_AREA_SOLICITANTE"));
                solicitudN.setSonNodoUsuario(obj.getString("SON_NODOUSUARIO"));
                solicitudN.setSonConfiabilidad(obj.getBigDecimal("SON_CONFIABILIDAD"));
                solicitudN.setSonManzanaCatastral(obj.getString("SON_MANZANACATASTRAL"));
                solicitudN.setSonActividadEconomica(obj.getString("SON_ACTIVIDADECONOMICA"));
                solicitudN.setSonFuente(obj.getString("SON_FUENTE"));
                solicitudN.setSonFechaIngreso(new Date(obj.getString("FECHA_INGRESO")));
                solicitudN.setSonFechaSolicitud(new Date(obj.getString("FECHA_SOLICITUD")));
                solicitudN.setSonFechaCreacion(new Date(obj.getString("FECHA_CREA")));
            }
            return solicitudN;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar solicitud de negocio EX000 " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param nombreFuncionalidad
     * @param user
     * @param solicitud
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public boolean updateSolicitudNegocio(String nombreFuncionalidad, String user, SolicitudNegocio solicitud) throws ApplicationException {
        boolean res = false;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            String[] args = new String[12];

            String marcas = null;
            if (solicitud.getMarcas() != null) {
                marcas = solicitud.getMarcas().getMarId().toString();
            }
            args[0] = solicitud.getNodo().getNodId().toString();
            args[1] = solicitud.getTipoHhppRed().getThrId().toString();
            args[2] = solicitud.getTipoHhppConexion().getThcId().toString();
            args[3] = marcas;
            args[4] = solicitud.getNodo().getNodHeadend();
            args[5] = solicitud.getSonResGestion();
            args[6] = solicitud.getSonTipoSolucion();
            args[7] = solicitud.getSonEstado();
            args[8] = solicitud.getSonUsuGestion();
            args[9] = solicitud.getSonUsuarioModificacion();
            args[10] = solicitud.getSonArchivoGeneradoRr();
            args[11] = solicitud.getSonId().toString();

            res = adb.in("son4", (Object[]) args);
            if (res) {
                SolicitudNegocio solpersisted = querySolicitudNegocio(solicitud.getSonId());
                AuditoriaEJB audi = new AuditoriaEJB();
                audi.auditar(nombreFuncionalidad, Constant.SOLICITUD_NEGOCIO, solpersisted.getSonUsuGestion(), Constant.UPDATE, solpersisted.auditoria(), adb);
            }
            DireccionUtil.closeConnection(adb);
            return res;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al actualizar la solicitud de negocio. EX000 " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param solicitud
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public boolean cancelSolicitudNegocio(SolicitudNegocio solicitud) throws ApplicationException {
        boolean res = false;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            res = adb.in("son5", solicitud.getSonUsuarioModificacion(), solicitud.getSonId().toString());
            DireccionUtil.closeConnection(adb);
            return res;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al cancelar la solicitud de negocio. EX000 " + e.getMessage(), e);
        }
    }

    /**
     * Procesar las solicitudes: crear HHPP para cada solicitud, cuando sea
     * posible y actualizar las solicitudes segun corresopnda.
     *
     * @param solicitudes Solicitudes a ser procesadas
     * @param nombreFuncionalidad nombre de la funcionalidad que solicita la
     * operación
     * @param nombreArchivo Nombre del archivo en el cual deben ser incluidas
     * las solciitudes que pasen a estado "ARCHIVO_GENERADO"
     * @param user
     * @return Mensaje informando resultado de la operación
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public String procesarSolicitudesNegocio(List<SolicitudNegocio> solicitudes, String nombreFuncionalidad, String nombreArchivo, String user) throws ApplicationException {
        String message = "";
        HhppEJB hhppEjb = new HhppEJB();

        for (int i = 0; i < solicitudes.size(); i++) {
            SolicitudNegocio solicitud = solicitudes.get(i);
            try {
                SolicitudNegocio solProcesada = hhppEjb.createHHPP(solicitud, nombreFuncionalidad, nombreArchivo, user);
                if (solProcesada.getSonUsuarioModificacion() != null && (solProcesada.getSonEstado().equals(Constant.ESTADO_SON_ARCH_GENERADO) || solProcesada.getSonEstado().equals(Constant.ESTADO_SON_RECHAZADA))) {
                    updateSolicitudNegocio(nombreFuncionalidad, user, solProcesada);
                }
            } catch (Exception ex) {
                String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
                LOGGER.error(msg);
            }
        }
        return message;
    }

    /**
     * Procesar las solicitudes: actualiza HHPP para cada solicitud, cuando sea
     * posible y actualiza las solicitudes segun corresponda.
     *
     * @param solicitudes Solicitudes a ser procesadas
     * @param nombreFuncionalidad nombre de la funcionalidad que solicita la
     * operación
     * @param nombreArchivo Nombre del archivo en el cual deben ser incluidas
     * las solicitudes que pasen a estado "ARCHIVO_GENERADO"
     * @param user
     * @return Mensaje informando resultado de la operación
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public String procesarSolicitudesModificacion(List<SolicitudNegocio> solicitudes, String nombreFuncionalidad, String nombreArchivo, String user) throws ApplicationException {
        try {
            String message = "";
            HhppEJB hhppEjb = new HhppEJB();
            AddressEJB addressEjb = new AddressEJB();
            for (int i = 0; i < solicitudes.size(); i++) {
                SolicitudNegocio solicitud = solicitudes.get(i);
                try {
                    if (solicitud.getSonTipoSolicitud().equals(Constant.TIPO_SON_VERIFICACION)) {
                        boolean resHhp = false;
                        resHhp = hhppEjb.updateEstado(solicitud.getHhpp().getHhppId(), solicitud.getSonEstadoUni(), nombreFuncionalidad, user);
                        if (resHhp) {
                            solicitud.setSonUsuarioModificacion(user);
                            solicitud.setSonEstado(Constant.ESTADO_SON_ARCH_GENERADO);
                            solicitud.setSonArchivoGeneradoRr(nombreArchivo);
                            updateSolicitudNegocio(nombreFuncionalidad, user, solicitud);
                        }
                    } else if (solicitud.getSonTipoSolicitud().equals(Constant.TIPO_SON_CAMBIONOD)) {
                        boolean resHhp = false;

                        BigDecimal nod_id = hhppEjb.queryNodobyCod(solicitud.getSonNodoUsuario());
                        if (nod_id != null) {
                            resHhp = hhppEjb.updateNodo(solicitud.getHhpp().getHhppId(), nod_id, nombreFuncionalidad, user);
                        } else {
                            message = message + "Error al procesar solicitud:" + solicitud.getSonId();
                        }

                        if (resHhp) {
                            solicitud.setSonUsuarioModificacion(user);
                            solicitud.setSonEstado(Constant.ESTADO_SON_ARCH_GENERADO);
                            solicitud.setSonArchivoGeneradoRr(nombreArchivo);
                            updateSolicitudNegocio(nombreFuncionalidad, user, solicitud);
                        }
                    } else if (solicitud.getSonTipoSolicitud().equals(Constant.TIPO_SON_GRID)) {
                        solicitud.setSonUsuarioModificacion(user);
                        solicitud.setSonEstado(Constant.ESTADO_SON_ARCH_GENERADO);
                        solicitud.setSonArchivoGeneradoRr(nombreArchivo);
                        updateSolicitudNegocio(nombreFuncionalidad, user, solicitud);
                    } else if (solicitud.getSonTipoSolicitud().equals(Constant.TIPO_SON_CAMBIOEST)) {
                        boolean resDir = false, resHhp = false;
                        BigDecimal subdir = null;
                        if (null != solicitud.getHhpp().getSubDireccion()) {
                            subdir = solicitud.getHhpp().getSubDireccion().getSdiId();
                        }
                        resDir = addressEjb.updateAddressEstrato(new BigDecimal(solicitud.getSonEstrato()), solicitud.getHhpp().getDireccion(), subdir, nombreFuncionalidad, user);
                        resHhp = hhppEjb.updateHhppUSerDate(solicitud.getHhpp().getHhppId(), nombreFuncionalidad, user);
                        if (resDir && resHhp) {
                            solicitud.setSonUsuarioModificacion(user);
                            solicitud.setSonEstado(Constant.ESTADO_SON_ARCH_GENERADO);
                            solicitud.setSonArchivoGeneradoRr(nombreArchivo);
                            updateSolicitudNegocio(nombreFuncionalidad, user, solicitud);
                        }
                    } else if (solicitud.getSonTipoSolicitud().equals(Constant.TIPO_SON_CAMBIODIR)) {
                        //TODO definir si lo que cambia son atributos de la dirección o si es una dirección completamente nueva
                        boolean resDir = true, resHhp = false;
                        //resDir = addressEjb.METODO Q ACTUALICE LO Q SE NECESITA
                        resHhp = hhppEjb.updateHhppUSerDate(solicitud.getHhpp().getHhppId(), nombreFuncionalidad, user);
                        if (resDir && resHhp) {
                            solicitud.setSonUsuarioModificacion(user);
                            solicitud.setSonEstado(Constant.ESTADO_SON_ARCH_GENERADO);
                            solicitud.setSonArchivoGeneradoRr(nombreArchivo);
                            updateSolicitudNegocio(nombreFuncionalidad, user, solicitud);
                        }
                    }
                } catch (Exception ex) {
                    message = message + "Error al procesar solicitud:" + solicitud.getSonId();
                    String msgError = "Error en " + ClassUtils.getCurrentMethodName(this.getClass()) + ": " + ex.getMessage();
                    LOGGER.error(msgError, ex);
                }
            }
            return message;
        } catch (IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al procesar las solicitudes de modificación. EX000 " + ex.getMessage(), ex);
        }
    }
}
