package co.com.telmex.catastro.services.solicitud;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.visitasTecnicas.business.NodoManager;
import co.com.telmex.catastro.data.DataShowSolicitudRed;
import co.com.telmex.catastro.data.DetalleSolicitud;
import co.com.telmex.catastro.data.Direccion;
import co.com.telmex.catastro.data.Marcas;
import co.com.telmex.catastro.data.SolicitudRed;
import co.com.telmex.catastro.services.hhpp.HhppEJB;
import co.com.telmex.catastro.services.seguridad.AuditoriaEJB;
import co.com.telmex.catastro.services.util.Constant;
import co.com.telmex.catastro.services.util.ConsultaSolRedModificacion;
import co.com.telmex.catastro.services.util.DireccionUtil;
import com.jlcg.db.AccessData;
import com.jlcg.db.data.DataList;
import com.jlcg.db.data.DataObject;
import com.jlcg.db.exept.ExceptionDB;
import com.jlcg.db.sql.ManageAccess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 * Clase SolicitudHHPPDisenioRedEJB implementa SolicitudHHPPDisenioRedEJBRemote
 *
 * @author Deiver Rovira
 * @version	1.0
 */
@Stateless(name = "solicitudHHPPDisenioRedEJB", mappedName = "solicitudHHPPDisenioRedEJB", description = "solicitudHHPPDisenioRed")
@Remote({SolicitudHHPPDisenioRedEJBRemote.class})
public class SolicitudHHPPDisenioRedEJB implements SolicitudHHPPDisenioRedEJBRemote {

    private static String MESSAGE_OK = "La operación fue correcta.";
    private static String MESSAGE_ERROR = "Fallo en la operación";
    NodoManager nodoManager = new NodoManager();
    private static final Logger LOGGER = LogManager.getLogger(SolicitudHHPPDisenioRedEJB.class);

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    @Override
    public List<ConsultaSolRedModificacion> querySolicitudesRedCreadas() throws ApplicationException {
        List<ConsultaSolRedModificacion> solicitudes = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("sre8");
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                solicitudes = new ArrayList<ConsultaSolRedModificacion>();
                for (DataObject obj : list.getList()) {
                    BigDecimal id_SolRed = obj.getBigDecimal("SRE_ID");

                    ConsultaSolRedModificacion solicitud = new ConsultaSolRedModificacion();
                    if (id_SolRed != null) {
                        solicitud.setId_SolRed(id_SolRed);
                    }

                    solicitudes.add(solicitud);
                }
            }
            return solicitudes;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar las solicitudes de red creadas. EX000 " + e.getMessage(), e);
        }
    }

    /**
     * Consultar solicitudes de Red pendientes por procesar, es decir q se
     * encuentren en estado CREACION
     *
     * @return lista de solicitudes de Red pendientes por procesar
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public List<ConsultaSolRedModificacion> querySolicitudesRedAProcesar() throws ApplicationException {
        List<ConsultaSolRedModificacion> solicitudes = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("sre9");
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                solicitudes = new ArrayList<ConsultaSolRedModificacion>();
                for (DataObject obj : list.getList()) {
                    

                    BigDecimal id_SolRed = obj.getBigDecimal("SRE_ID");
                    BigDecimal id_detSol = obj.getBigDecimal("DET_ID");
                    BigDecimal id_nodo = obj.getBigDecimal("NODO_ID");
                    BigDecimal id_hhpp = obj.getBigDecimal("HHPP_ID");
                    String dso_apto = obj.getString("DSO_APTO");
                    String dso_dir = obj.getString("DSO_DIR");
                    String dso_dir_sta = obj.getString("DSO_DIR_STA");
                    String dso_nombre = obj.getString("DSO_NOMBRE");
                    String dso_fuente = obj.getString("DSO_FUENTE");
                    String dso_dir_alt = obj.getString("DSO_DIR_ALT");
                    String dso_dir_alt_sta = obj.getString("DSO_DIR_ALT_STA");
                    String geo_localidad = obj.getString("GEO_LOCALIDAD");
                    String geo_barrio = obj.getString("GEO_BARRIO");
                    String geo_manzana = obj.getString("GEO_MANZANA");
                    String dso_estado_hhpp = obj.getString("DSO_ESTADO_HHPP");
                    BigDecimal dso_estrato = obj.getBigDecimal("DSO_ESTRATO");

                    ConsultaSolRedModificacion solicitud = new ConsultaSolRedModificacion();
                    if (id_SolRed != null) {
                        solicitud.setId_SolRed(id_SolRed);
                    }
                    if (id_detSol != null) {
                        solicitud.setId_detSol(id_detSol);
                    }
                    if (id_nodo != null) {
                        solicitud.setId_nodo(id_nodo);
                    }
                    if (id_hhpp != null) {
                        solicitud.setId_hhpp(id_hhpp);
                    }
                    solicitud.setDso_apto(dso_apto);
                    solicitud.setDso_dir(dso_dir);
                    solicitud.setDso_dir_alt(dso_dir_alt);
                    solicitud.setDso_dir_alt_sta(dso_dir_alt_sta);
                    solicitud.setDso_dir_sta(dso_dir_sta);
                    solicitud.setDso_fuente(dso_fuente);
                    solicitud.setDso_nombre(dso_nombre);
                    solicitud.setGeo_localidad(geo_localidad);
                    solicitud.setGeo_barrio(geo_barrio);
                    solicitud.setGeo_manzana(geo_manzana);
                    solicitud.setDso_estado_hhpp(dso_estado_hhpp);
                    solicitud.setDso_estrato(dso_estrato);
                    solicitudes.add(solicitud);
                }
            }
            return solicitudes;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar solicitudes de red a procesar. EX000 " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param nuevaDireccion
     * @param nombreFuncionalidad
     * @param user
     * @return
     * @throws ExceptionDB
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public String updateDireccion(Direccion nuevaDireccion, String nombreFuncionalidad, String user) throws ExceptionDB, ApplicationException {
        String msj = "";
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            //En este punto se debe extraer los datos que se desean modificar. p.e: estado
            String idDir = "";
            String nuevoEstrato = "";
            try {
                idDir = nuevaDireccion.getDirId().toString();
                nuevoEstrato = nuevaDireccion.getDirEstrato().toString();
            } catch (Exception e) {
                throw new ApplicationException("Error: los datos de la direccion" + nuevaDireccion.getDirId() + " son incorrectos.");
            }
            boolean ok = false;

            ok = adb.in("dir14", nuevoEstrato, user, idDir);
            if (ok) {
                AuditoriaEJB audi = new AuditoriaEJB();
                audi.auditar(nombreFuncionalidad, Constant.DIRECCION, user, Constant.UPDATE, nuevaDireccion.auditoria(), adb);
                msj = MESSAGE_OK;
            } else {
                msj = MESSAGE_ERROR;
            }
            DireccionUtil.closeConnection(adb);
            return msj;
        } catch (ApplicationException | ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al actualizar la dirección. EX000 " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param estadoFinal
     * @param fileName
     * @param user
     * @param nombreFuncionalidad
     * @param detalleSolicitud
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws ExceptionDB
     */
    @Override
    public String updateDetalleSolicitudRed(String estadoFinal, String fileName, String user, String nombreFuncionalidad, DetalleSolicitud detalleSolicitud) throws ApplicationException {
        String msj = "";
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            //En este punto se debe extraer los datos que se desean modificar. p.e: estado
            String idDir = "";
            String nuevoEstrato = "";



            DireccionUtil.closeConnection(adb);
            return msj;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al actualizar el detalle de la solicitud de red. EX000 " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param idSolRed
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public List<ConsultaSolRedModificacion> queryDetalleSolicitudBySolRedId(String idSolRed) throws ApplicationException {
        List<ConsultaSolRedModificacion> solicitudes = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("sre9", idSolRed);
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                solicitudes = new ArrayList<ConsultaSolRedModificacion>();
                for (DataObject obj : list.getList()) {
                    

                    BigDecimal id_SolRed = obj.getBigDecimal("SRE_ID");
                    BigDecimal id_detSol = obj.getBigDecimal("DET_ID");
                    BigDecimal id_hhpp = obj.getBigDecimal("HHPP_ID");
                    String dso_apto = obj.getString("DSO_APTO");
                    String dso_dir = obj.getString("DSO_DIR");
                    String dso_dir_sta = obj.getString("DSO_DIR_STA");
                    String dso_nombre = obj.getString("DSO_NOMBRE");
                    String dso_fuente = obj.getString("DSO_FUENTE");
                    String dso_dir_alt = obj.getString("DSO_DIR_ALT");
                    String dso_dir_alt_sta = obj.getString("DSO_DIR_ALT_STA");
                    String geo_localidad = obj.getString("GEO_LOCALIDAD");
                    String geo_barrio = obj.getString("GEO_BARRIO");
                    String geo_manzana = obj.getString("GEO_MANZANA");
                    String dso_estado_hhpp = obj.getString("DSO_ESTADO_HHPP");
                    BigDecimal dso_estrato = obj.getBigDecimal("DSO_ESTRATO");

                    ConsultaSolRedModificacion solicitud = new ConsultaSolRedModificacion();
                    if (id_SolRed != null) {
                        solicitud.setId_SolRed(id_SolRed);
                    }
                    if (id_detSol != null) {
                        solicitud.setId_detSol(id_detSol);
                    }

                    if (id_hhpp != null) {
                        solicitud.setId_hhpp(id_hhpp);
                    }
                    solicitud.setDso_apto(dso_apto);
                    solicitud.setDso_dir(dso_dir);
                    solicitud.setDso_dir_alt(dso_dir_alt);
                    solicitud.setDso_dir_alt_sta(dso_dir_alt_sta);
                    solicitud.setDso_dir_sta(dso_dir_sta);
                    solicitud.setDso_fuente(dso_fuente);
                    solicitud.setDso_nombre(dso_nombre);
                    solicitud.setGeo_localidad(geo_localidad);
                    solicitud.setGeo_barrio(geo_barrio);
                    solicitud.setGeo_manzana(geo_manzana);
                    solicitud.setDso_estado_hhpp(dso_estado_hhpp);
                    solicitud.setDso_estrato(dso_estrato);
                    solicitudes.add(solicitud);
                }
            }
            return solicitudes;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar el detalle de la solicitud por SolRedId. EX000 " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param idSolRed
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public List<ConsultaSolRedModificacion> queryDetalleSolicitudBySolRedIdValidos(String idSolRed) throws ApplicationException {
        List<ConsultaSolRedModificacion> solicitudes = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("sre10", idSolRed);
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                solicitudes = new ArrayList<ConsultaSolRedModificacion>();
                for (DataObject obj : list.getList()) {
                    

                    BigDecimal id_SolRed = obj.getBigDecimal("SRE_ID");
                    BigDecimal id_detSol = obj.getBigDecimal("DET_ID");
                    BigDecimal id_nodo = obj.getBigDecimal("NODO_ID");
                    BigDecimal id_hhpp = obj.getBigDecimal("HHPP_ID");
                    String dso_apto = obj.getString("DSO_APTO");
                    String dso_dir = obj.getString("DSO_DIR");
                    String dso_dir_sta = obj.getString("DSO_DIR_STA");
                    String dso_nombre = obj.getString("DSO_NOMBRE");
                    String dso_fuente = obj.getString("DSO_FUENTE");
                    String dso_dir_alt = obj.getString("DSO_DIR_ALT");
                    String dso_dir_alt_sta = obj.getString("DSO_DIR_ALT_STA");
                    String geo_localidad = obj.getString("GEO_LOCALIDAD");
                    String geo_barrio = obj.getString("GEO_BARRIO");
                    String geo_manzana = obj.getString("GEO_MANZANA");
                    String dso_estado_hhpp = obj.getString("DSO_ESTADO_HHPP");
                    BigDecimal dso_estrato = obj.getBigDecimal("DSO_ESTRATO");
                    String dso_geoNodo1 = obj.getString("DSO_ND1_GEO");
                    String dso_geoNodo2 = obj.getString("DSO_ND2_GEO");
                    String dso_geoNodo3 = obj.getString("DSO_ND3_GEO");

                    ConsultaSolRedModificacion solicitud = new ConsultaSolRedModificacion();
                    if (id_SolRed != null) {
                        solicitud.setId_SolRed(id_SolRed);
                    }
                    if (id_detSol != null) {
                        solicitud.setId_detSol(id_detSol);
                    }
                    if (id_nodo != null) {
                        solicitud.setId_nodo(id_nodo);
                    }
                    if (id_hhpp != null) {
                        solicitud.setId_hhpp(id_hhpp);
                    }
                    solicitud.setDso_apto(dso_apto);
                    solicitud.setDso_dir(dso_dir);
                    solicitud.setDso_dir_alt(dso_dir_alt);
                    solicitud.setDso_dir_alt_sta(dso_dir_alt_sta);
                    solicitud.setDso_dir_sta(dso_dir_sta);
                    solicitud.setDso_fuente(dso_fuente);
                    solicitud.setDso_nombre(dso_nombre);
                    solicitud.setGeo_localidad(geo_localidad);
                    solicitud.setGeo_barrio(geo_barrio);
                    solicitud.setGeo_manzana(geo_manzana);
                    solicitud.setDso_estado_hhpp(dso_estado_hhpp);
                    solicitud.setDso_estrato(dso_estrato);
                    solicitud.setDso_nd1_geo(dso_geoNodo1);
                    solicitud.setDso_nd2_geo(dso_geoNodo2);
                    solicitud.setDso_nd3_geo(dso_geoNodo3);
                    solicitudes.add(solicitud);
                }
            }
            return solicitudes;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar el detalle de solicitud por SolRedId validos. EX000 " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param estadoFinal
     * @param fileName
     * @param user
     * @param nombreFuncionalidad
     * @param solRed
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public String updateSolicitudRedById(String estadoFinal, String fileName, String user, String nombreFuncionalidad, SolicitudRed solRed) throws ApplicationException {
        String msj = "";
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            //En este punto se debe extraer los datos que se desean modificar. p.e: estado
            String idSolRed = "";
            try {
                idSolRed = solRed.getSreId().toString();
            } catch (Exception e) {
                String msg = "Se produjo un error al momento de ejecutar el método '"+
                        ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
                throw new ApplicationException("Error: los datos de la solicitud de red" + solRed.getSreId().toString() + " son incorrectos.");
            }
            boolean ok = false;
            try {

                ok = adb.in("sre11", estadoFinal, fileName, user, idSolRed);
                if (ok) {
                    AuditoriaEJB audi = new AuditoriaEJB();
                    audi.auditar(nombreFuncionalidad, Constant.SOLICITUD_RED, user, Constant.UPDATE, solRed.auditoria(), adb);
                    DireccionUtil.closeConnection(adb);
                    return MESSAGE_OK;
                } else {
                    DireccionUtil.closeConnection(adb);
                    return MESSAGE_ERROR;
                }
            } catch (ExceptionDB exdb) {
                msj = "Error actualizando la Solicitud de red: " + idSolRed;
                LOGGER.error(exdb);
            }
            DireccionUtil.closeConnection(adb);
            return msj;
        } catch (ApplicationException | ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al actualizar la solicitud de red por Id. EX000 " + e.getMessage(), e);
        }
    }

    /**
     * Consultar las solicitudes de Red pendientes por procesar
     *
     * @return lista de solicitudes de Red que se encuentran en estado creado y
     * cuyos detalles tambein estan en estado creado
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public List<DataShowSolicitudRed> querySolicitudesProcesar() throws ApplicationException {
        List<DataShowSolicitudRed> solicitudes = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("sre12");
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                solicitudes = new ArrayList<DataShowSolicitudRed>();
                for (DataObject obj : list.getList()) {
                    BigDecimal id_SolRed = obj.getBigDecimal("SRE_ID");
                    String observaciones = obj.getString("SRE_OBSERVACIONES");
                    String gpoNombre = obj.getString("GPO_NOMBRE");
                    String fecha = obj.getString("FECHA");
                    String nodnombre = obj.getString("NOD_NOMBRE");
                    String NumRegistros = obj.getBigDecimal("NUMREGISTROS").toString();
                    String Estado = obj.getString("ESTADO");
                    DataShowSolicitudRed solicitud = new DataShowSolicitudRed();
                    if (id_SolRed != null) {
                        solicitud.setId(id_SolRed);
                        solicitud.setObservaciones(observaciones);
                        solicitud.setCiudad(gpoNombre);
                        solicitud.setFechaCreacion(fecha);
                        solicitud.setNodo(nodnombre);
                        solicitud.setRegistros(NumRegistros);
                        solicitud.setEstado(Estado);
                    }
                    solicitudes.add(solicitud);
                }
            }
            return solicitudes;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar las solicitudes de Red pendientes por procesar. EX000 " + e.getMessage(), e);
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
    public boolean updateSolicitudRed(String nombreFuncionalidad, String user, SolicitudRed solicitud) throws ApplicationException {
        boolean res = false;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            String[] args = new String[12];

            //UPDATE SOLICITUD_RED SET SRE_USUARIO_MODIFICACION='?',SRE_ESTADO='?',SRE_ARCHIVO_GENERADO_RR='?',SRE_FECHA_MODIFICACION=CURRENT_DATE WHERE SRE_ID=?
            SolicitudRedEJB solredEjb = new SolicitudRedEJB();
            args[0] = solicitud.getSreUsuarioModificacion();
            args[1] = solicitud.getSreEstado();
            args[3] = solicitud.getSreId().toString();

            res = adb.in("sre13", (Object[]) args);
            if (res) {
                SolicitudRed solpersisted = solredEjb.querySolicitudNegocio(solicitud.getSreId());
                AuditoriaEJB audi = new AuditoriaEJB();
                audi.auditar(nombreFuncionalidad, Constant.SOLICITUD_NEGOCIO, solpersisted.getSreUsuarioModificacion(), Constant.UPDATE, solpersisted.auditoria(), adb);
            }
            DireccionUtil.closeConnection(adb);
            return res;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al actualizar la solicitud de red. EX000 " + e.getMessage(), e);
        }
    }

    /**
     * Consultar detalles de solicitud de Red q pertenecen a una Solicitud de
     * red especifica
     *
     * @param idSre Identificador único de la solicitud de red
     * @return lista de detalles de solicitud pertenecientes a la solicitud de
     * red con id idsre
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws ExceptionDB
     */
    @Override
    public List<DetalleSolicitud> queryDetallesSolicitudBySolRedId(BigDecimal idSre) throws ApplicationException {
        List<DetalleSolicitud> detalles = null;

        return detalles;
    }

    /**
     *
     * @param idSolRed
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public List<DetalleSolicitud> queryDetalleSolicitudCreacionBySolRedIdValidos(String idSolRed) throws ApplicationException {
        List<DetalleSolicitud> solicitudesred = null;

        return solicitudesred;
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
    public String procesarSolicitudesCreacionRed(DetalleSolicitud solicitudes, String nombreFuncionalidad, String nombreArchivo, String user) throws ApplicationException {
        String message = MESSAGE_OK;
        HhppEJB hhppEjb = new HhppEJB();
        
        return message;
    }

    /**
     *
     * @param detalleAConsultar
     * @param subdirIdDir
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @Override
    public ConsultaSolRedModificacion complementarSalidaModHHPP(ConsultaSolRedModificacion detalleAConsultar, BigDecimal subdirIdDir) throws ApplicationException {
        boolean res = false;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            String query = "";
            if (subdirIdDir != null) {
                query = "cssd";
            } else {
                query = "csd1";
            }
            DataObject obj = adb.outDataObjec(query, detalleAConsultar.getId_hhpp().toString());
            if (obj != null) {
                String codcomun = obj.getString("IDCOM");
                String nodo = obj.getString("NOD_CODIGO");
                String barrio = obj.getString("BARRIO");
                BigDecimal estrato = obj.getBigDecimal("ESTRATO");
                String direccion = obj.getString("DIA_DIRECCION_IGAC");
                String localidad = obj.getString("LOCALIDAD");
                String manzana = obj.getString("DIR_MANZANA_CATASTRAL");
                String EstadoUni = obj.getString("ESTADOUNIDAD");
                String tipoUni = obj.getString("TIPOUNIDAD");
                String tipoacometida = obj.getString("TIPOACOMETIDA");
                String tipoConexion = obj.getString("THR_CODIGO");

                detalleAConsultar.setComunidadFromNodo(codcomun);
                detalleAConsultar.setCod_nodo(nodo);
                detalleAConsultar.setGeo_barrio(barrio);
                detalleAConsultar.setDso_estrato(estrato);
                detalleAConsultar.setDso_dir(direccion);
                detalleAConsultar.setGeo_localidad(localidad);
                detalleAConsultar.setGeo_manzana(manzana);
                detalleAConsultar.setDso_estado_hhpp(EstadoUni);
                detalleAConsultar.setTipoHhppFromHhpp(tipoUni);
                detalleAConsultar.setTipoacometida(tipoacometida);
                detalleAConsultar.setTipoconexion(tipoConexion);
                List<Marcas> listaMarcas = buscarMarcas(detalleAConsultar.getId_hhpp().toString());
                detalleAConsultar.setListaMarcas(listaMarcas);
            }
            DireccionUtil.closeConnection(adb);
            return detalleAConsultar;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al complementar la modificación. EX000 " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param idHHPP
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws ExceptionDB
     */
    public List<Marcas> buscarMarcas(String idHHPP) throws ApplicationException {
        List<Marcas> listaMarcas = null;
        Marcas marca = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("marc");
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                listaMarcas = new ArrayList<Marcas>();
                for (DataObject obj : list.getList()) {
                    String smarca = obj.getString("MAR_CODIGO");
                    marca = new Marcas();
                    marca.setMarCodigo(smarca);
                    listaMarcas.add(marca);
                }
            }
            return listaMarcas;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al buscar marcas. EX000 " + e.getMessage(), e);
        }
    }
}
