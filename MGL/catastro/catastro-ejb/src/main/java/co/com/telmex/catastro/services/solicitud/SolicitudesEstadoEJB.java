package co.com.telmex.catastro.services.solicitud;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.visitasTecnicas.business.NodoManager;
import co.com.telmex.catastro.data.Comunidad;
import co.com.telmex.catastro.data.Geografico;
import co.com.telmex.catastro.data.GeograficoPolitico;
import co.com.telmex.catastro.data.HhppConsulta;
import co.com.telmex.catastro.data.Marcas;
import co.com.telmex.catastro.data.Nodo;
import co.com.telmex.catastro.data.SolicitudNegocio;
import co.com.telmex.catastro.data.SolicitudRed;
import co.com.telmex.catastro.data.TipoHhpp;
import co.com.telmex.catastro.data.TipoHhppConexion;
import co.com.telmex.catastro.data.TipoHhppRed;
import co.com.telmex.catastro.data.TipoMarcas;
import co.com.telmex.catastro.services.seguridad.AuditoriaEJB;
import co.com.telmex.catastro.services.util.Constant;
import co.com.telmex.catastro.services.util.DireccionUtil;
import com.jlcg.db.AccessData;
import com.jlcg.db.data.DataList;
import com.jlcg.db.data.DataObject;
import com.jlcg.db.exept.ExceptionDB;
import com.jlcg.db.sql.ManageAccess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;

/**
 * Clase SolicitudesEstadoEJB implementa SolicitudesEstadoEJBRemote
 *
 * @author Ana María Malambo
 * @version	1.0
 */
@Stateless(name = "solicitudesEstadoEJB", mappedName = "solicitudesEstadoEJB", description = "solicitudEstado")
@Remote({SolicitudesEstadoEJBRemote.class})
public class SolicitudesEstadoEJB implements SolicitudesEstadoEJBRemote {

    private static String MESSAGE_OK = "La operación fue correcta.";
    private static String MESSAGE_ERROR = "Fallo en la operación";
    NodoManager nodoManager = new NodoManager();
    private static final Logger LOGGER = LogManager.getLogger(SolicitudesEstadoEJB.class);

    /**
     * Lista los ids de las direcciones que coinciden con la direccion ingresada
     * (Estandarizada)
     *
     * @param direccion
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<HhppConsulta> queryHhppByIdDir(String direccion) throws ApplicationException {
        List<HhppConsulta> listHhpp = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("dir3", direccion+"%");
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                listHhpp = new ArrayList<HhppConsulta>();
                for (DataObject obj : list.getList()) {
                    BigDecimal hhp_id = obj.getBigDecimal("HHP_ID");
                    String hhp_estado = obj.getString("EHH_NOMBRE");
                    String dir_igac = obj.getString("DIR_FORMATO_IGAC");
                    String dir_cuenta_matriz = obj.getString("DIR_CUENTA_MATRIZ");
                    HhppConsulta hhpp = new HhppConsulta();
                    hhpp.setIdentificador(hhp_id);
                    hhpp.setEstado(hhp_estado);
                    hhpp.setDireccionEstandar(dir_igac);
                    hhpp.setCuentaMatriz(dir_cuenta_matriz);
                    listHhpp.add(hhpp);
                }
            }
            return listHhpp;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar HHPP por dirección. EX000 " + e.getMessage(), e);
        }
    }

    /**
     * Carga todas las Solicitudes de negocio con estado != a cancelado/cerrado
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public List<SolicitudNegocio> querySolicitudesNegocio() throws ApplicationException {
        List<SolicitudNegocio> listNegocio = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("son7");
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                listNegocio = new ArrayList<SolicitudNegocio>();
                for (DataObject obj : list.getList()) {
                    String son_nombre_archivo = obj.getString("SON_ARCHIVO_GENERADO_RR");
                    SolicitudNegocio solNeg = new SolicitudNegocio();
                    solNeg.setSonArchivoGeneradoRr(son_nombre_archivo);
                    listNegocio.add(solNeg);
                }
            }
            return listNegocio;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar solicitudes de negocio. EX000 " + e.getMessage(), e);
        }
    }

    /**
     * Carga todas las Solicitudes de Red con estado != a cancelado/cerrado
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public List<SolicitudRed> querySolicitudesRed() throws ApplicationException {
        List<SolicitudRed> listRed = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("sre4");
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                listRed = new ArrayList<SolicitudRed>();
                for (DataObject obj : list.getList()) {
                    String son_nombre_archivo = obj.getString("SRE_ARCHIVO_GENERADO_RR");
                    SolicitudRed solRed = new SolicitudRed();
                    listRed.add(solRed);
                }
            }
            return listRed;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar solicitudes de red. EX000 " + e.getMessage(), e);
        }
    }

    /**
     * Carga los estados finales de acuerdo al estado inicial del HHPP
     *
     * @param idEstadoInicial
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public List<SelectItem> queryEstadosFinales(String idEstadoInicial) throws ApplicationException {
        List<SelectItem> listEstadosFinales = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("teh1", idEstadoInicial);
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                listEstadosFinales = new ArrayList<SelectItem>();
                for (DataObject obj : list.getList()) {
                    SelectItem item = new SelectItem();
                    BigDecimal idEstado_final = obj.getBigDecimal("EHH_ID2");
                    String nombreEstado_final = obj.getString("EHH_NOMBRE");
                    item.setValue(idEstado_final.toString());
                    item.setLabel(nombreEstado_final);
                    listEstadosFinales.add(item);
                }
            }
            return listEstadosFinales;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar estados finales. EX000 " + e.getMessage(), e);
        }
    }

    /**
     * Consulta la solicitud de Negocio por el id
     *
     * @param id
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public SolicitudNegocio querySolNegocioById(String id) throws ApplicationException {
        SolicitudNegocio solNeg = null;

        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("son8", id);
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                String gpo_nombre = obj.getString("GPO_NOMBRE");
                GeograficoPolitico gpo = new GeograficoPolitico();
                gpo.setGpoNombre(gpo_nombre);

                String nodo_nombre = obj.getString("NOD_NOMBRE");
                Nodo nodo = new Nodo();
                nodo.setNodNombre(nodo_nombre);

                String thh_valor = obj.getString("THH_VALOR");
                String thh_id = obj.getString("THH_ID");
                TipoHhpp thh = new TipoHhpp();
                thh.setThhId(thh_id);
                thh.setThhValor(thh_valor);

                String geo_nombre = obj.getString("GEO_NOMBRE");
                Geografico geo = new Geografico();
                geo.setGeoNombre(geo_nombre);

                String thr_codigo = obj.getString("THR_CODIGO");
                TipoHhppRed thr = new TipoHhppRed();
                thr.setThrCodigo(thr_codigo);

                String thc_codigo = obj.getString("THC_CODIGO");
                TipoHhppConexion thc = new TipoHhppConexion();
                thc.setThcCodigo(thc_codigo);

                String tma_codigo = obj.getString("TMA_NOMBRE");
                TipoMarcas tma = new TipoMarcas();
                tma.setTmaCodigo(tma_codigo);

                String son_cuentaMatriz = obj.getString("SON_CUENTA_MATRIZ");
                String son_motivo = obj.getString("SON_MOTIVO");
                String son_nomSolicitante = obj.getString("SON_NOM_SOLICITANTE");
                String son_codSolicitante = obj.getString("SON_COD_SOLICITANTE");
                String son_email = obj.getString("SON_EMAIL_SOLICITANTE");
                String son_cel_solicitante = obj.getString("SON_CEL_SOLICITANTE");
                String son_nomContacto = obj.getString("SON_CONTACTO");
                String son_telContacto = obj.getString("SON_TEL_CONTACTO");
                String son_estado = obj.getString("SON_ESTADO");
                String son_observaciones = obj.getString("SON_OBSERVACIONES");
                String son_tipoSolicitud = obj.getString("SON_TIPO_SOLICITUD");
                String son_estado_sol = obj.getString("SON_ESTADO_SOL");
                String son_formatoIgac = obj.getString("SON_FORMATO_IGAC");
                String son_noStandar = obj.getString("SON_NOSTANDAR");
                String son_estrato = obj.getString("SON_ESTRATO");
                String son_zipcode = obj.getString("SON_ZIPCODE");
                String son_dirAlterna = obj.getString("SON_DIRALTERNA");
                String son_localidad = obj.getString("SON_LOCALIDAD");
                String son_mz = obj.getString("SON_MZ");
                String son_longitud = obj.getString("SON_LONGITUD");
                String son_latitud = obj.getString("SON_LATITUD");

                //Se crea el objeto con el resultado de la consulta
                solNeg = new SolicitudNegocio();
                solNeg.setGeografico(geo);
                solNeg.setGeograficoPolitico(gpo);
                solNeg.setNodo(nodo);
                solNeg.setTipoHhppConexion(thc);
                solNeg.setTipoHhppRed(thr);
                solNeg.setTipoHhpp(thh);
                solNeg.setSonMotivo(son_motivo);
                solNeg.setSonNomSolicitante(son_nomSolicitante);
                solNeg.setSonCodSolicitante(son_codSolicitante);
                solNeg.setSonEmailSolicitante(son_email);
                solNeg.setSonCelSolicitante(son_cel_solicitante);
                solNeg.setSonContacto(son_nomContacto);
                solNeg.setSonTelContacto(son_telContacto);
                solNeg.setSonEstado(son_estado);
                solNeg.setSonObservaciones(son_observaciones);
                solNeg.setSonFormatoIgac(son_formatoIgac);
                solNeg.setSonNostandar(son_noStandar);
                solNeg.setSonTipoSolicitud(son_tipoSolicitud);
                solNeg.setSonEstadoSol(son_estado_sol);
                solNeg.setSonEstrato(son_estrato);
                solNeg.setSonZipcode(son_zipcode);
                solNeg.setSonDiralterna(son_dirAlterna);
                solNeg.setSonLocalidad(son_localidad);
                solNeg.setSonMz(son_mz);
                solNeg.setSonLongitud(son_longitud);
                solNeg.setSonLatitud(son_latitud);
            }
            return solNeg;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error consultar soliditudes de negocio por id. EX000 " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param fileName
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public SolicitudNegocio querySolNegocioByFileName(String fileName) throws ApplicationException {
        SolicitudNegocio solNeg = null;

        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("son10", fileName);
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                String son_nombre_archivo = obj.getString("SON_ARCHIVO_GENERADO_RR");
                solNeg = new SolicitudNegocio();
                solNeg.setSonArchivoGeneradoRr(son_nombre_archivo);
            }
            return solNeg;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar solicitudes de negocio por nombre de archivo. EX000 " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param fileName
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public List<SolicitudNegocio> querySolNegociosByFileName(String fileName) throws ApplicationException {

        List<SolicitudNegocio> listNegocio = null;
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
        Date date;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("son11", fileName);
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                listNegocio = new ArrayList<SolicitudNegocio>();
                for (DataObject obj : list.getList()) {
                    SolicitudNegocio solicitudN = new SolicitudNegocio();
                    ConsultaSolicitudEJB consultaEjb = new ConsultaSolicitudEJB();
                    //SN.SON_ID,SN.GPO_ID,N.NOD_ID,N.NOD_NOMBRE,N.NOD_CODIGO,N.NOD_HEAD_END,N.NOD_TIPO,N.COM_ID,SN.THH_ID,SN.GEO_ID,SN.THR_ID,SN.THC_ID,SN.TMA_ID,
                    solicitudN.setSonId(obj.getBigDecimal("SON_ID"));
                    BigDecimal id_gpo = obj.getBigDecimal("GEOGRAFICO_POLITICO_ID");
                    Nodo nodo = new Nodo();
                    nodo.setNodId(obj.getBigDecimal("NODO_ID"));
                    nodo.setNodNombre(obj.getString("NOD_NOMBRE"));
                    nodo.setNodCodigo(obj.getString("NOD_CODIGO"));
                    nodo.setNodHeadend(obj.getString("NOD_HEAD_END"));
                    nodo.setNodTipo(obj.getBigDecimal("NOD_TIPO"));
                    Comunidad comunidad = new Comunidad();
                    comunidad.setComId(obj.getString("COM_ID"));
                    nodo.setComunidad(comunidad);
                    String id_thh = obj.getString("THH_ID");
                    BigDecimal id_geo = obj.getBigDecimal("GEO_ID");
                    BigDecimal id_thr = obj.getBigDecimal("THR_ID");
                    BigDecimal id_thc = obj.getBigDecimal("THC_ID");
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
                    solicitudN.setNodo(nodo);
                    solicitudN.setTipoHhpp(thhpp);
                    solicitudN.setGeografico(geo);
                    solicitudN.setTipoHhppRed(thr);
                    solicitudN.setTipoHhppConexion(thc);
                    solicitudN.setMarcas(ma);
                    solicitudN.setSonMotivo(obj.getString("SON_MOTIVO"));
                    solicitudN.setSonNomSolicitante(obj.getString("SON_NOM_SOLICITANTE"));
                    solicitudN.setSonContacto(obj.getString("SON_CONTACTO"));
                    solicitudN.setSonTelContacto(obj.getString("SON_TEL_CONTACTO"));
                    String fecha_ingreso = obj.getString("FECHA_INGRESO").toLowerCase();
                    if (fecha_ingreso != null) {
                        date = (Date) formatter.parse(fecha_ingreso);
                        solicitudN.setSonFechaIngreso(date);
                    }
                    String fecha_solicitud = obj.getString("FECHA_SOLICITUD").toLowerCase();
                    if (fecha_solicitud != null) {
                        date = (Date) formatter.parse(fecha_solicitud);
                        solicitudN.setSonFechaSolicitud(date);
                    }
                    solicitudN.setSonEstado(obj.getString("SON_ESTADO"));
                    solicitudN.setSonTipoSolucion(obj.getString("SON_TIPO_SOLUCION"));
                    solicitudN.setSonResGestion(obj.getString("SON_RES_GESTION"));
                    solicitudN.setSonCorregirHhpp(obj.getString("SON_CORREGIR_HHPP"));
                    solicitudN.setSonCambioNodo(obj.getString("SON_CAMBIO_NODO"));
                    solicitudN.setSonNuevoProducto(obj.getString("SON_NUEVO_PRODUCTO"));
                    solicitudN.setSonEstratoAntiguo(obj.getString("SON_ESTRATO_ANTIGUO"));
                    solicitudN.setSonEstratoNuevo(obj.getString("SON_ESTRATO_NUEVO"));
                    solicitudN.setSonUsuarioCreacion(obj.getString("SON_USUARIO_CREACION"));
                    solicitudN.setSonObservaciones(obj.getString("SON_OBSERVACIONES"));
                    solicitudN.setSonFormatoIgac(obj.getString("SON_FORMATO_IGAC"));
                    solicitudN.setSonServinformacion(obj.getString("SON_SERVINFORMACION"));
                    solicitudN.setSonNostandar(obj.getString("SON_NOSTANDAR"));
                    solicitudN.setSonTipoSolicitud(obj.getString("SON_TIPO_SOLICITUD"));
                    solicitudN.setSonEstrato(obj.getString("SON_ESTRATO"));
                    solicitudN.setSonNivSocioeconomico(obj.getString("SON_NIVSOCIOECONOMICO"));
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
                    solicitudN.setSonEstadoSol(obj.getString("SON_ESTADO_SOL"));
                    solicitudN.setSonEstadoUni(obj.getString("SON_ESTADO_UNI"));
                    solicitudN.setSonUsuGestion(obj.getString("SON_USU_GESTION"));
                    solicitudN.setSonCampoa1(obj.getString("SON_CAMPOA1"));
                    solicitudN.setSonCampoa2(obj.getString("SON_CAMPOA2"));
                    solicitudN.setSonCampoa3(obj.getString("SON_CAMPOA3"));
                    solicitudN.setSonCampoa4(obj.getString("SON_CAMPOA4"));
                    solicitudN.setSonCampoa5(obj.getString("SON_CAMPOA5"));
                    solicitudN.setSonDirExiste(obj.getString("SON_DIREXISTE"));
                    solicitudN.setSonDirValidada(obj.getString("SON_DIRVALIDADA"));
                    solicitudN.setSonNodoUsuario(obj.getString("SON_NODOUSUARIO"));
                    String fecha_creacion = obj.getString("FECHA_CREA").toLowerCase();
                    if (fecha_creacion != null) {
                        date = (Date) formatter.parse(fecha_creacion);
                        solicitudN.setSonFechaCreacion(date);
                    }
                    listNegocio.add(solicitudN);
                }
            }
            return listNegocio;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar solicitudes de negocio por nombre de archivo. EX000 " + e.getMessage(), e);
        }
    }

    /**
     * Consulta la Solicitud de Red por el id indicado por parametro
     *
     * @param id
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public SolicitudRed querySolRedById(String id) throws ApplicationException {

        SolicitudRed solRed = null;
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
        Date date;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("sre5", id);
            DireccionUtil.closeConnection(adb);
            
            if (obj != null) {
                //Se almcena el rsesultado de la consulta en el objeto a devolver
                solRed = new SolicitudRed();
                BigDecimal sre_id = obj.getBigDecimal("SRE_ID");
                BigDecimal gpo_id = obj.getBigDecimal("GEOGRAFICO_POLITICO_ID");
                String observaciones = obj.getString("SRE_OBSERVACIONES");
                String fecha_creacion = obj.getString("SRE_FECHA_CREACION");
                if (fecha_creacion != null) {
                    date = (Date) formatter.parse(fecha_creacion);
                    solRed.setSreFechaCreacion(date);
                }
                String fecha_modificacion = obj.getString("SRE_FECHA_MODIFICACION");
                if (fecha_modificacion != null) {
                    date = (Date) formatter.parse(fecha_modificacion);
                    solRed.setSreFechaModificacion(date);
                }
                String user_creacion = obj.getString("SRE_USUARIO_CREACION");
                String gpo_nombre = obj.getString("GPO_NOMBRE");
                String user_modificacion = obj.getString("SRE_USUARIO_MODIFICACION");
                String estado = obj.getString("SRE_ESTADO");
                String tiempo_horas = obj.getString("SRE_TIEMPOHORAS");
                String nombreArchivo = obj.getString("SRE_ARCHIVO_GENERADO_RR");
                String email = obj.getString("SRE_EMAIL_SOLICITANTE");

                GeograficoPolitico geo = new GeograficoPolitico();
                geo.setGpoId(gpo_id);
                geo.setGpoNombre(gpo_nombre);
                solRed.setSreId(sre_id);
                solRed.setGeograficoPolitico(geo);
                solRed.setSreObservaciones(observaciones);
                solRed.setSreUsuarioCreacion(user_creacion);
                solRed.setSreEstado(estado);
                solRed.setSreUsuarioModificacion(user_modificacion);

            }
            return solRed;
        } catch (ExceptionDB | ParseException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar solicitud de red por id EX000 " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param fileName
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public List<SolicitudRed> querySolRedesByFileName(String fileName) throws ApplicationException {

        List<SolicitudRed> listRed = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("sre7", fileName);
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
            Date date;
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                listRed = new ArrayList<SolicitudRed>();
                for (DataObject obj : list.getList()) {
                    BigDecimal sre_id = obj.getBigDecimal("SRE_ID");
                    String sre_observacion = obj.getString("SRE_OBSERVACIONES");
                    String sre_usuarioCreacion = obj.getString("SRE_USUARIO_CREACION");
                    String sre_usuarioModi = obj.getString("SRE_USUARIO_MODIFICACION");
                    String sre_estadoSol = obj.getString("SRE_ESTADO");
                    String sre_tiempoHoras = obj.getString("SRE_TIEMPOHORAS");
                    String sre_archivoGenerado = obj.getString("SRE_ARCHIVO_GENERADO_RR");
                    String sre_correo = obj.getString("SRE_EMAIL_SOLICITANTE");
                    SolicitudRed solRed = new SolicitudRed();
                    solRed.setSreId(sre_id);
                    solRed.setSreObservaciones(sre_observacion);
                    String fecha_creacion = obj.getString("FECHA_CREACION").toLowerCase();
                    if (fecha_creacion != null) {
                        date = (Date) formatter.parse(fecha_creacion);
                        solRed.setSreFechaCreacion(date);
                    }
                    String fecha_modificacion = obj.getString("FECHA_MODIFICACION");
                    if (fecha_modificacion != null) {
                        date = (Date) formatter.parse(fecha_modificacion);
                        solRed.setSreFechaModificacion(date);
                    }
                    solRed.setSreUsuarioCreacion(sre_usuarioCreacion);
                    solRed.setSreUsuarioModificacion(sre_usuarioModi);
                    solRed.setSreEstado(sre_estadoSol);

                    listRed.add(solRed);
                }
            }
            return listRed;
        } catch (ExceptionDB | ParseException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar solicitudes de red por nombre de archivo. EX000 " + e.getMessage(), e);
        }
    }

    /**
     * Actualiza el estado de la Solicitud de Negocio por el estado enviado por
     * parametro
     *
     * @param idEstadoFinal
     * @param user
     * @param solNeg
     * @param nombreFuncionalidad
     * @param idSolNeg
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public String updateStateSolNegocioById(String idEstadoFinal, String user, SolicitudNegocio solNeg, String idSolNeg, String nombreFuncionalidad) throws ApplicationException {
        String mensaje = "";
        String tiempoHoras = null;
        AccessData adb = null;
        try {
            if (solNeg.getSonTiempohoras() != null) {
                tiempoHoras = solNeg.getSonTiempohoras();
            } else {
                tiempoHoras = "0";
            }
            try {
                adb = ManageAccess.createAccessData();
                boolean ok = adb.in("son9", idEstadoFinal, user, tiempoHoras, idSolNeg);

                if (ok) {
                    AuditoriaEJB audi = new AuditoriaEJB();
                    audi.auditar(nombreFuncionalidad, Constant.SOLICITUD_NEGOCIO, user, Constant.UPDATE, solNeg.auditoria(), adb);
                    DireccionUtil.closeConnection(adb);
                    return MESSAGE_OK;
                } else {
                    DireccionUtil.closeConnection(adb);
                    return MESSAGE_ERROR;
                }
            } catch (ExceptionDB exdb) {
                DireccionUtil.closeConnection(adb);
                mensaje = exdb.getMessage();
            }
            return mensaje;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al actualizar el estdo de solicitud de negocio. EX000 " + e.getMessage(), e);
        }
    }

    /**
     * Actualiza el estado de la Solicitud de Negocio por el estado enviado por
     * parametro
     *
     * @param idEstadoFinal
     * @param idSolRed
     * @param solRed
     * @param user
     * @param nombreFuncionalidad
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public String updateStateSolRedById(String idEstadoFinal, String user, SolicitudRed solRed, String idSolRed, String nombreFuncionalidad) throws ApplicationException {
        String mensaje = "";
        String tiempoHoras = null;
        AccessData adb = null;
        try {
            try {
                adb = ManageAccess.createAccessData();
                boolean ok = adb.in("sre6", idEstadoFinal, user, String.valueOf(tiempoHoras), idSolRed);
                DireccionUtil.closeConnection(adb);
                if (ok) {
                    return MESSAGE_OK;
                } else {
                    return MESSAGE_ERROR;
                }
            } catch (ExceptionDB exdb) {
                DireccionUtil.closeConnection(adb);
                mensaje = exdb.getMessage();
            }
            return mensaje;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al actualizar el estado de la solicitud de negocio. EX000 " + e.getMessage(), e);
        }
    }

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
}