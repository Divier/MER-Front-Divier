package co.com.telmex.catastro.services.solicitud;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.visitasTecnicas.business.NodoManager;
import co.com.telmex.catastro.data.Comunidad;
import co.com.telmex.catastro.data.Direccion;
import co.com.telmex.catastro.data.GeograficoPolitico;
import co.com.telmex.catastro.data.SolicitudConsulta;
import co.com.telmex.catastro.data.TipoGeografico;
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
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 * Clase ConsultaSolicitudEJB implementa ConsultaSolicitudEJBRemote
 *
 * @author Ana María Malambo
 * @version	1.0
 */
@Stateless(name = "consultaSolicitudEJB", mappedName = "consultaSolicitudEJB", description = "consultaSolicitud")
@Remote({ConsultaSolicitudEJBRemote.class})
public class ConsultaSolicitudEJB implements ConsultaSolicitudEJBRemote {

    private static String MESSAGE_OK = "La operacion fue correcta.";
    private static String MESSAGE_ERROR = "Fallo en la operacion";
    NodoManager manager = new NodoManager();
    private static final Logger LOGGER = LogManager.getLogger(ConsultaSolicitudEJB.class);

    /**
     *
     * @param direccion
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public List<SolicitudConsulta> queryCuentaMatriz(String direccion) throws ApplicationException {
        List<SolicitudConsulta> direcciones = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("cons1", direccion);
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                direcciones = new ArrayList<SolicitudConsulta>();
                for (DataObject obj : list.getList()) {


                    BigDecimal dir_id = obj.getBigDecimal("SDI_ID");
                    String dir = obj.getString("SDI_FORMATO_IGAC");
                    String act = obj.getString("DIR_ACTIVIDAD_ECONO");
                    BigDecimal nivel = obj.getBigDecimal("DIR_NIVEL_SOCIOECONO");

                    BigDecimal estrato = obj.getBigDecimal("DIR_ESTRATO");
                    SolicitudConsulta dirc = new SolicitudConsulta();
                    dirc.setIdentificador(dir_id);
                    dirc.setActividadec(act);
                    dirc.setNivsocio(nivel);
                    dirc.setDireccionm(dir);
                    dirc.setEstrato(estrato);
                    direcciones.add(dirc);
                }
            }
            return direcciones;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar la cuenta matriz. EX000 " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param estratos
     * @param direccion
     * @param user
     * @param funcionalidad
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public List<SolicitudConsulta> modificarEst(String estratos, String direccion, String user, String funcionalidad) throws ApplicationException {
        List<SolicitudConsulta> direccionesM = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            String[] parameter = new String[2];
            parameter[0] = estratos;
            parameter[1] = direccion;
            String result = processIUD("cons2", parameter);
            if (result.equals("La operacion fue correcta")) {
                SolicitudConsulta sol = new SolicitudConsulta();
                AuditoriaEJB audi = new AuditoriaEJB();
                audi.auditar(funcionalidad, Constant.SOLICITUD_NEGOCIO, user, Constant.UPDATE, sol.auditoria(), adb);

            }
            DataList list = adb.outDataList("cons1", direccion);
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                direccionesM = new ArrayList<SolicitudConsulta>();
                for (DataObject obj : list.getList()) {

                    BigDecimal dir_id = obj.getBigDecimal("SDI_ID");
                    String dir = obj.getString("SDI_FORMATO_IGAC");

                    String act = obj.getString("DIR_ACTIVIDAD_ECONO");
                    BigDecimal nivel = obj.getBigDecimal("DIR_NIVEL_SOCIOECONO");

                    BigDecimal estrato = obj.getBigDecimal("DIR_ESTRATO");


                    SolicitudConsulta dirc = new SolicitudConsulta();
                    dirc.setIdentificador(dir_id);
                    dirc.setActividadec(act);
                    dirc.setNivsocio(nivel);
                    dirc.setDireccionm(dir);
                    dirc.setEstrato(estrato);

                    direccionesM.add(dirc);
                }
            }
            return direccionesM;
        } catch (ApplicationException | ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al modificar el estrato. EX000 " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param ctl_sql
     * @param parameter
     * @return
     * @throws ExceptionDB
     */
    private String processIUD(String ctl_sql, String parameter[]) throws ApplicationException {
        String message = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            boolean ok = adb.in(ctl_sql, (Object[]) parameter);
            DireccionUtil.closeConnection(adb);
            if (ok) {
                message = MESSAGE_OK;
            } else {
                message = MESSAGE_ERROR;
            }
            return message;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al procesar IUD. EX000 " + e.getMessage(), e);
        }
    }

    /**
     * Valida si la direccion existe en el repositorio
     *
     * @param dirFormatoIGAC
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public boolean validateExistAddressIntoRepo(String dirFormatoIGAC) throws ApplicationException {
        boolean res = false;
        List<Direccion> direcciones = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("dir1", dirFormatoIGAC);
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                direcciones = new ArrayList<Direccion>();
                for (DataObject obj : list.getList()) {
                    BigDecimal dir_id = obj.getBigDecimal("DIR_ID");
                    Direccion dir = new Direccion();
                    dir.setDirId(dir_id);
                    direcciones.add(dir);
                }
            }
            if (direcciones != null) {
                res = true;
            }
            return res;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al validar la existencia de la dirección. EX000 " + e.getMessage(), e);
        }
    }

    /**
     * Valida si la subdireccion existe en el repositorio
     *
     * @param subdirFormatoIGAC
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public boolean validateExistSubAddressIntoRepo(String subdirFormatoIGAC) throws ApplicationException {
        boolean res = false;
        //TODO Eliminar
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("sdi1", subdirFormatoIGAC);
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                res = true;
            }
            return res;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al validar la existencia de la subdirección. EX000 " + e.getMessage(), e);
        }
    }

    /**
     * Consultar un tipo de HHPP
     *
     * @param idTipoHhpp
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public TipoHhpp queryTipoHhpp(String idTipoHhpp) throws ApplicationException {
        TipoHhpp tipoHhpp = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("thh2", idTipoHhpp);
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                tipoHhpp = new TipoHhpp();
                tipoHhpp.setThhId(obj.getString("THH_ID"));
                tipoHhpp.setThhValor(obj.getString("THH_VALOR"));
            }
            return tipoHhpp;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar el tipo HHPP. EX000 " + e.getMessage(), e);
        }
    }

    /**
     * Consultar un tipo de Conexion
     *
     * @param idTipoHhppConexion
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public TipoHhppConexion queryTipoHhppConexion(BigDecimal idTipoHhppConexion) throws ApplicationException {
        TipoHhppConexion tipoHhppConexion = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("thc2", idTipoHhppConexion.toString());
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                tipoHhppConexion = new TipoHhppConexion();
                tipoHhppConexion.setThcId(obj.getBigDecimal("THC_ID"));
                tipoHhppConexion.setThcCodigo(obj.getString("THC_CODIGO"));
                tipoHhppConexion.setThcNombre(obj.getString("THC_NOMBRE"));
            }
            return tipoHhppConexion;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar tipo de conexción HHPP. EX000 " + e.getMessage(), e);
        }
    }

    /**
     * Consultar un tipo de Red
     *
     * @param idTipoHhppRed
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public TipoHhppRed queryTipoHhppRed(BigDecimal idTipoHhppRed) throws ApplicationException {
        TipoHhppRed tipoHhppRed = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("thr2", idTipoHhppRed.toString());
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                tipoHhppRed = new TipoHhppRed();
                tipoHhppRed.setThrId(obj.getBigDecimal("THR_ID"));
                tipoHhppRed.setThrCodigo(obj.getString("THR_CODIGO"));
                tipoHhppRed.setThrNombre(obj.getString("THR_NOMBRE"));
            }
            return tipoHhppRed;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar el tipo de red HHPP. EX000 " + e.getMessage(), e);
        }
    }

    /**
     * Consultar un tipo de Red
     *
     * @param codigo
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public TipoHhppRed queryTipoHhppRedByCodigo(String codigo) throws ApplicationException {
        TipoHhppRed tipoHhppRed = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("thr3", codigo);
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                //SELECT THR_ID,THR_CODIGO,THR_NOMBRE FROM TIPO_HHPP_RED WHERE THR_CODIGO='?'
                tipoHhppRed = new TipoHhppRed();
                tipoHhppRed.setThrId(obj.getBigDecimal("THR_ID"));
                tipoHhppRed.setThrCodigo(obj.getString("THR_CODIGO"));
                tipoHhppRed.setThrNombre(obj.getString("THR_NOMBRE"));
            }
            return tipoHhppRed;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar el tipo de conexión y red HHPP por código. EX000 " + e.getMessage(), e);
        }
    }

    /**
     * Consultar un tipo de Marcas
     *
     * @param idTipoMarcas
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public TipoMarcas queryTipoMarcas(BigDecimal idTipoMarcas) throws ApplicationException {
        TipoMarcas tipoMarcas = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("tma2", idTipoMarcas.toString());
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                tipoMarcas = new TipoMarcas();
                tipoMarcas.setTmaId(obj.getBigDecimal("TMA_ID"));
                tipoMarcas.setTmaCodigo(obj.getString("TMA_CODIGO"));
                tipoMarcas.setTmaNombre(obj.getString("TMA_NOMBRE"));
            }
            return tipoMarcas;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error eal consultar el tipo de marcas. EX000 " + e.getMessage(), e);
        }
    }

    @Override
    public GeograficoPolitico queryGeograficoPolitico(BigDecimal gpo_id) throws ApplicationException {
        return manager.queryGeograficoPolitico(gpo_id);
    }

    @Override
    public TipoGeografico queryTipoGeografico(BigDecimal tge_id) throws ApplicationException {
        return manager.queryTipoGeografico(tge_id);
    }

    @Override
    public Comunidad queryComunidad(String idComunidad) throws ApplicationException {
        return manager.queryComunidad(idComunidad);
    }
}
