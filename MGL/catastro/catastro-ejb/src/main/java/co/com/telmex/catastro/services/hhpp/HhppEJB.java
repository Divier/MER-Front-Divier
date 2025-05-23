package co.com.telmex.catastro.services.hhpp;

import co.com.claro.cmas400.ejb.respons.HhppId;
import co.com.claro.cmas400.ejb.respons.HhppPaginationResponse;
import co.com.claro.mgl.businessmanager.address.HhppMglLogRrManager;
import co.com.claro.mgl.businessmanager.address.HhppMglManager;
import co.com.claro.mgl.businessmanager.address.MglParametrosTrabajosDetailManager;
import co.com.claro.mgl.businessmanager.address.MglParametrosTrabajosManager;
import co.com.claro.mgl.businessmanager.address.NodoMglManager;
import co.com.claro.mgl.businessmanager.address.rr.As400Manager;
import co.com.claro.mgl.businessmanager.cm.CmtBasicaMglManager;
import co.com.claro.mgl.businessmanager.cm.HhppPaginationManager;
import co.com.claro.mgl.dtos.HhppPaginationRequestDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.HhppMglLogRr;
import co.com.claro.mgl.jpa.entities.MglParametrosTrabajos;
import co.com.claro.mgl.jpa.entities.MglParametrosTrabajosDetail;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.PaginacionDto;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.rr.HhppRR;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.visitasTecnicas.business.ConsultaEspecificaManager;
import co.com.claro.visitasTecnicas.business.DireccionRRManager;
import co.com.claro.visitasTecnicas.business.HHPPManager;
import co.com.claro.visitasTecnicas.entities.HhppResponseQueryRegularUnitRR;
import co.com.telmex.catastro.data.*;
import co.com.telmex.catastro.services.georeferencia.*;
import co.com.telmex.catastro.services.seguridad.AuditoriaEJB;
import co.com.telmex.catastro.services.solicitud.*;
import co.com.telmex.catastro.services.util.*;
import com.jlcg.db.AccessData;
import com.jlcg.db.data.DataList;
import com.jlcg.db.data.DataObject;
import com.jlcg.db.exept.ExceptionDB;
import com.jlcg.db.sql.ManageAccess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;

/**
 * Clase HhppEJB implementa HhppEJBRemote
 *
 * @author Deiver Rovira.
 * @version	1.0
 */
@Stateless(name = "hhppEJB", mappedName = "hhppEJB", description = "hhpp")
@Remote({HhppEJBRemote.class})
public class HhppEJB implements HhppEJBRemote {

    private final Usuario user = null;
    private static final String MESSAGE_OK = "La operación fue correcta.";
    private static final String MESSAGE_ERROR = "Fallo en la operación";
    private String DOMINIO = "";
    private final HHPPManager manager = new HHPPManager();
    private static final Logger LOGGER = LogManager.getLogger(HhppEJB.class);
   
    private final CmtBasicaMglManager cmtBasicaMglManager = new CmtBasicaMglManager();
    private final MglParametrosTrabajosDetailManager mglParametrosTrabajosDetailManager
            = new MglParametrosTrabajosDetailManager();
    private final MglParametrosTrabajosManager mglParametrosTrabajosManager = new MglParametrosTrabajosManager();
    private final HhppMglManager hhppMglManager = new HhppMglManager();
    private final NodoMglManager nodoMglManager = new NodoMglManager();
    private final HhppMglLogRrManager hhppMglLogRrManager = new HhppMglLogRrManager();
  
    /**
     *
     */
    public HhppEJB() {
        queryParametrosConfig();
    }

    /**
     * Carga las variables de configuración globales
     */
    private void queryParametrosConfig() {
        ResourceEJB recursos = new ResourceEJB();

        Parametros param = null;
        try {
            param = recursos.queryParametros(Constant.DOMINIO);
            if (param != null) {
                DOMINIO = param.getValor();
            }
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
        }
    }

    /**
     * Lista los ids de las direcciones que coinciden con la direccion ingresada
     * (Estandarizada)
     *
     * @param direccion
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public List<HhppConsulta> queryHhppByIdDir(String direccion) throws ApplicationException {
        List<HhppConsulta> listHhpp = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("dir3", direccion + "%");
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                listHhpp = new ArrayList<HhppConsulta>();
                for (DataObject obj : list.getList()) {
                    BigDecimal hhp_id = obj.getBigDecimal("HHP_ID");
                    BigDecimal hpp_idEstado = obj.getBigDecimal("EHH_ID");
                    String hhp_estado = obj.getString("EHH_NOMBRE");
                    String dir_igac = obj.getString("DIR_FORMATO_IGAC");
                    String dir_cuenta_matriz = obj.getString("UBI_CUENTA_MATRIZ");
                    BigDecimal tipoRed = obj.getBigDecimal("THR_ID ");
                    HhppConsulta hhpp = new HhppConsulta();
                    hhpp.setIdentificador(hhp_id);
                    hhpp.setIdEstadoInicial(hpp_idEstado.toString());
                    hhpp.setEstado(hhp_estado);
                    hhpp.setDireccionEstandar(dir_igac);
                    hhpp.setCuentaMatriz(dir_cuenta_matriz);
                    hhpp.setIdTipoRed(tipoRed);
                    listHhpp.add(hhpp);
                }
            }
            return listHhpp;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar los HHPP por dirección. EX000: " + e.getMessage(), e);
        }
    }

    /**
     * Carga el Hhpp asociado a una dirección
     *
     * @param idDir identificar único de la dirección.
     * @return Id de hhpp y id de cada uno de las entidades con las que tiene
     * relación el Hhpp. Hhpp asociado a la dirección con id idDir.
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public Hhpp queryHhppByIdDireccion(BigDecimal idDir) throws ApplicationException {
        Hhpp hhpp = null;

        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("hhp6", idDir.toString());
            DireccionUtil.closeConnection(adb);
            //SELECT HH.HHP_ID,HH.SDI_ID,DIR.DIR_ID,DIR.DIR_ESTRATO,DIR.DIR_NIVEL_SOCIOECONO,NOD.NOD_ID,NOD.NOD_CODIGO,NOD.NOD_NOMBRE,NOD.NOD_TIPO, NOD.COM_ID,
            //TH.THH_ID,TH.THH_VALOR,THC.THC_ID,THC.THC_CODIGO,THC.THC_NOMBRE,THR.THR_ID,THR.THR_CODIGOTHR.THR_NOMBRE,EHH.EHH_ID , EHH.EHH_NOMBRE
            if (obj != null) {
                hhpp = new Hhpp();
                hhpp.setHhppId(obj.getBigDecimal("HHP_ID"));

                //SubDireccion
                BigDecimal id_sdi = obj.getBigDecimal("SDI_ID");
                SubDireccion subdi = new SubDireccion();
                subdi.setSdiId(id_sdi);

                //Dirección
                BigDecimal dir_id = obj.getBigDecimal("DIR_ID");
                Direccion dir = new Direccion();
                dir.setDirId(dir_id);
                dir.setDirEstrato(obj.getBigDecimal("DIR_ESTRATO"));
                dir.setDirNivelSocioecono(obj.getBigDecimal("DIR_NIVEL_SOCIOECONO"));

                //Nodo
                BigDecimal nod_id = obj.getBigDecimal("NODO_ID");
                Nodo nodo = new Nodo();
                nodo.setNodId(nod_id);
                nodo.setNodCodigo(obj.getString("NOD_CODIGO"));
                nodo.setNodNombre(obj.getString("NOD_NOMBRE"));
                nodo.setNodTipo(obj.getBigDecimal("NOD_TIPO"));
                Comunidad comunidad = new Comunidad();
                comunidad.setComId(obj.getString("COM_ID"));
                nodo.setComunidad(comunidad);

                //TipoHhpp {}
                String thh_id = obj.getString("THH_ID");
                TipoHhpp tipoHhpp = new TipoHhpp();
                tipoHhpp.setThhId(thh_id);
                tipoHhpp.setThhValor(obj.getString("THH_VALOR"));

                //Tipo Conexión
                BigDecimal id_thc = obj.getBigDecimal("THC_ID");
                TipoHhppConexion tipoHc = new TipoHhppConexion();
                tipoHc.setThcId(id_thc);
                tipoHc.setThcCodigo(obj.getString("THC_CODIGO"));
                tipoHc.setThcNombre(obj.getString("THC_NOMBRE"));

                //Tipo Red
                BigDecimal id_thr = obj.getBigDecimal("THR_ID");
                TipoHhppRed tipoRed = new TipoHhppRed();
                tipoRed.setThrId(id_thr);
                tipoRed.setThrCodigo(obj.getString("THR_CODIGO"));
                tipoRed.setThrNombre(obj.getString("THR_NOMBRE"));

                //Estado Hhpp
                String id_ehh = obj.getString("EHH_ID");
                EstadoHhpp estado = new EstadoHhpp();
                estado.setEhhId(id_ehh);
                estado.setEhhNombre(obj.getString("EHH_NOMBRE"));

                //Se arma el objeto de respuesta con los datos consultados
                hhpp.setDireccion(dir);
                hhpp.setSubDireccion(subdi);
                hhpp.setNodo(nodo);
                hhpp.setTipoHhpp(tipoHhpp);
                hhpp.setSubDireccion(subdi);
                hhpp.setTipoConexionHhpp(tipoHc);
                hhpp.setTipoRedHhpp(tipoRed);
                hhpp.setEstadoHhpp(estado);
            }
            return hhpp;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar el HHPP por dirección. EX000: " + e.getMessage(), e);
        }
    }

    /**
     * Lista los ids de las sub-direcciones que coinciden con la direccion
     * ingresada (Estandarizada)
     *
     * @param subDireccion
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public List<HhppConsulta> queryHhppByIdSubDir(String subDireccion) throws ApplicationException {
        List<HhppConsulta> listHhpp = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("sdi5", subDireccion + "%");
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                listHhpp = new ArrayList<HhppConsulta>();
                for (DataObject obj : list.getList()) {
                    BigDecimal hhp_id = obj.getBigDecimal("HHP_ID");
                    BigDecimal hpp_idEstado = obj.getBigDecimal("EHH_ID");
                    String hhp_estado = obj.getString("EHH_NOMBRE");
                    String dir_igac = obj.getString("SDI_FORMATO_IGAC");
                    HhppConsulta hhpp = new HhppConsulta();
                    hhpp.setIdentificador(hhp_id);
                    hhpp.setIdEstadoInicial(hpp_idEstado.toString());
                    hhpp.setEstado(hhp_estado);
                    hhpp.setDireccionEstandar(dir_igac);
                    listHhpp.add(hhpp);
                }
            }
            return listHhpp;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar los HHPP por subdirección. EX000: " + e.getMessage(), e);
        }
    }

    /**
     * Carga el Hhpp asociado a una dirección
     *
     * @param idsubDir
     * @return Id de hhpp y id de cada uno de las entidades con las que tiene
     * relación el Hhpp. Hhpp asociado a la dirección con id idDir.
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public Hhpp queryHhppByIdSubDireccion(BigDecimal idsubDir) throws ApplicationException {
        Hhpp hhpp = null;

        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("hhp7", idsubDir.toString());
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                hhpp = new Hhpp();
                BigDecimal id_hhpp = obj.getBigDecimal("HHP_ID");
                hhpp.setHhppId(id_hhpp);
                //SubDireccion
                BigDecimal id_sdi = obj.getBigDecimal("SDI_ID");
                SubDireccion subdi = new SubDireccion();
                subdi.setSdiId(id_sdi);
                subdi.setSdiEstrato(obj.getBigDecimal("SDI_ESTRATO"));
                subdi.setSdiNivelSocioecono(obj.getBigDecimal("SDI_NIVEL_SOCIOECONO"));

                //Dirección
                BigDecimal dir_id = obj.getBigDecimal("DIR_ID");
                Direccion dir = new Direccion();
                dir.setDirId(dir_id);
                BigDecimal ubi_id = obj.getBigDecimal("UBI_ID");
                Ubicacion ubi = new Ubicacion();
                ubi.setUbiId(ubi_id);
                dir.setUbicacion(ubi);

                //Nodo
                BigDecimal nod_id = obj.getBigDecimal("NODO_ID");
                Nodo nodo = new Nodo();
                nodo.setNodId(nod_id);
                nodo.setNodCodigo(obj.getString("NOD_CODIGO"));
                nodo.setNodNombre(obj.getString("NOD_NOMBRE"));
                nodo.setNodTipo(obj.getBigDecimal("NOD_TIPO"));
                Comunidad comunidad = new Comunidad();
                comunidad.setComId(obj.getString("COM_ID"));
                nodo.setComunidad(comunidad);

                //TipoHhpp {}
                String thh_id = obj.getString("THH_ID");
                TipoHhpp tipoHhpp = new TipoHhpp();
                tipoHhpp.setThhId(thh_id);
                tipoHhpp.setThhValor(obj.getString("THH_VALOR"));

                //Tipo Conexión
                BigDecimal id_thc = obj.getBigDecimal("THC_ID");
                TipoHhppConexion tipoHc = new TipoHhppConexion();
                tipoHc.setThcId(id_thc);
                tipoHc.setThcCodigo(obj.getString("THC_CODIGO"));
                tipoHc.setThcNombre(obj.getString("THC_NOMBRE"));

                //Tipo Red
                BigDecimal id_thr = obj.getBigDecimal("THR_ID");
                TipoHhppRed tipoRed = new TipoHhppRed();
                tipoRed.setThrId(id_thr);
                tipoRed.setThrCodigo(obj.getString("THR_CODIGO"));
                tipoRed.setThrNombre(obj.getString("THR_NOMBRE"));

                //Estado Hhpp
                String id_ehh = obj.getString("EHH_ID");
                EstadoHhpp estado = new EstadoHhpp();
                estado.setEhhId(id_ehh);
                estado.setEhhNombre(obj.getString("EHH_NOMBRE"));

                //Se arma el objeto de respuesta con los datos consultados
                hhpp.setDireccion(dir);
                hhpp.setSubDireccion(subdi);
                hhpp.setNodo(nodo);
                hhpp.setTipoHhpp(tipoHhpp);
                hhpp.setSubDireccion(subdi);
                hhpp.setTipoConexionHhpp(tipoHc);
                hhpp.setTipoRedHhpp(tipoRed);
                hhpp.setEstadoHhpp(estado);
            }
            return hhpp;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar el HHPP por subdirección. EX000: " + e.getMessage(), e);
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
                listEstadosFinales = new ArrayList<>();
                for (DataObject obj : list.getList()) {
                    SelectItem item = new SelectItem();
                    String idEstado_final = obj.getString("EHH_ID2");
                    String nombreEstado_final = obj.getString("EHH_NOMBRE");
                    item.setValue(idEstado_final);
                    item.setLabel(nombreEstado_final);
                    listEstadosFinales.add(item);
                }
            }
            return listEstadosFinales;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar los estados finales. EX000: " + e.getMessage(), e);
        }
    }

    /**
     * Consulta el HHPP por el Id enviado por parametro
     *
     * @param idHhpp
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public Hhpp queryHhppById(String idHhpp) throws ApplicationException {
        Hhpp homePass = null;

        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("hhp1", idHhpp);
            DireccionUtil.closeConnection(adb);
            ConsultaEspecificaManager manager1 = new ConsultaEspecificaManager();
            //"select distinct h.dir_id, h.sdi_id, h.ehh_id, e.ehh_nombre, t.thh_id, t.thh_valor,n.nod_id, n.nod_nombre,n.NOD_CODIGO,n.COM_ID,n.NOD_HEAD_END,
            //n.NOD_TIPO,tc.thc_id, tc.thc_codigo, tr.thr_id, tr.thr_codigo from hhpp h, tipo_hhpp t, nodo n, tipo_hhpp_conexion tc, tipo_hhpp_red tr, estado_hhpp e
            //where hhp_id ='?' and h.nod_id = n.nod_id and h.thh_id = t.thh_id and h.thc_id = tc.thc_id and h.thr_id = tr.thr_id and h.ehh_id = e.ehh_id"
            if (obj != null) {
                homePass = new Hhpp();
                BigDecimal dir_id = obj.getBigDecimal("DIR_ID");
                Direccion dir = null;
                if (dir_id != null) {
                    dir = manager1.queryAddressOnRepoById(dir_id.toString());
                }
                //Subdireccion
                BigDecimal sdi_id = obj.getBigDecimal("SDI_ID");
                SubDireccion sdir = null;
                if (sdi_id != null) {
                    sdir = manager1.querySubAddressOnRepositoryById(sdi_id);
                }
                //id Estado
                String hhp_idEstado = obj.getString("EHH_ID");
                //nombre Estado
                String hhp_nombre_estado = obj.getString("EHH_NOMBRE");
                EstadoHhpp ehhp = new EstadoHhpp();
                ehhp.setEhhId(hhp_idEstado);
                ehhp.setEhhNombre(hhp_nombre_estado);
                //Tipo de HHPP
                String thh_valor = obj.getString("THH_VALOR");
                String thh_id = obj.getString("THH_ID");
                TipoHhpp tipohhpp = new TipoHhpp();
                tipohhpp.setThhId(thh_id);
                tipohhpp.setThhValor(thh_valor);
                //Nodo
                String nodo_nombre = obj.getString("NOD_NOMBRE");
                String nodo_headEnd = obj.getString("NOD_HEAD_END");
                BigDecimal nodo_tipo = obj.getBigDecimal("NOD_TIPO");
                String nodo_codigo = obj.getString("NOD_CODIGO");
                BigDecimal nodo_id = obj.getBigDecimal("NODO_ID");

                String nodo_comunidad = obj.getString("COM_ID");
                Nodo nodo = new Nodo();
                nodo.setNodId(nodo_id);
                Comunidad comunidad = new Comunidad();
                comunidad.setComId(nodo_comunidad);
                nodo.setComunidad(comunidad);
                nodo.setNodNombre(nodo_nombre);
                nodo.setNodHeadend(nodo_headEnd);
                nodo.setNodTipo(nodo_tipo);
                nodo.setNodCodigo(nodo_codigo);
                //Tipo Conexion
                String thc_codigo = obj.getString("THC_CODIGO");
                BigDecimal thc_id = obj.getBigDecimal("THC_ID");
                TipoHhppConexion thc = new TipoHhppConexion();
                thc.setThcId(thc_id);
                thc.setThcCodigo(thc_codigo);
                //Tipo de Red
                String thr_codigo = obj.getString("THR_CODIGO");
                BigDecimal thr_id = obj.getBigDecimal("THR_ID");
                TipoHhppRed thr = new TipoHhppRed();
                thr.setThrId(thr_id);
                thr.setThrCodigo(thr_codigo);

                List<Marcas> marcasHhpp = queryMarcasHhppByIdHhpp(idHhpp);
                //Se arma el objeto de respuesta con los datos consultados
                homePass.setHhppId(new BigDecimal(idHhpp));
                homePass.setDireccion(dir);
                homePass.setSubDireccion(sdir);
                homePass.setEstadoHhpp(ehhp);
                homePass.setTipoHhpp(tipohhpp);
                homePass.setNodo(nodo);
                homePass.setTipoConexionHhpp(thc);
                homePass.setTipoRedHhpp(thr);
                homePass.setMarcas(marcasHhpp);
            }
            return homePass;
        } catch (ApplicationException | ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar el HHPP por ID. EX000: " + e.getMessage(), e);
        }
    }

    /**
     * Actualiza el estado del HHPP por el estado enviado por parametro
     *
     * @param idEstadoFinal
     * @param user
     * @param hhpp
     * @param nombreFuncionalidad
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public String updateEstadoHhpp(String idEstadoFinal, String user, Hhpp hhpp, String nombreFuncionalidad) throws ApplicationException {
        String mensaje;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            boolean ok = adb.in("hhp2", idEstadoFinal, user, hhpp.getHhppId().toString());

            if (ok) {
                AuditoriaEJB audi = new AuditoriaEJB();
                audi.auditar(nombreFuncionalidad, Constant.HHPP, user, Constant.UPDATE, hhpp.auditoria(), adb);
                DireccionUtil.closeConnection(adb);
                mensaje = MESSAGE_OK;
            } else {
                DireccionUtil.closeConnection(adb);
                mensaje = MESSAGE_ERROR;
            }

            return mensaje;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de actualizar el estado del HHPP. EX000: " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param codigoNodo
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public BigDecimal queryNodobyCod(String codigoNodo) throws ApplicationException {
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("nod6", codigoNodo);
            BigDecimal codigo_nodo = null;
            if (codigoNodo != null) {
                codigo_nodo = obj.getBigDecimal("NODO_ID");
            }
            DireccionUtil.closeConnection(adb);
            return codigo_nodo;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar el nodo por código. EX000: " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param idCiudad
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public String queryTipoCiudadByID(String idCiudad) throws ApplicationException {
        String tipoCiudad = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("gpo6", idCiudad);
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                tipoCiudad = obj.getString("GPO_COD_TIPO_DIRECCION");
            }
            return tipoCiudad;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar el tipo de ciudad por ID. EX000: " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param idCiudad
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public boolean queryCiudadMultiorigen(String idCiudad) throws ApplicationException {
        boolean multiorigen = false;
        String ciudad;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("gpo7", idCiudad);
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                ciudad = obj.getString("GPO_MULTIORIGEN");
                if (ciudad != null && "1".equals(ciudad)) {
                    multiorigen = Boolean.TRUE;
                }
            }
            return multiorigen;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar la ciudad multiorigen. EX000: " + e.getMessage(), e);
        }
    }

    /**
     * Determina si la dirección es cuenta matriz
     *
     * @param codDireccion
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws ExceptionDB
     */
    @Override
    public boolean queryCuentaMatrizOnDir(String codDireccion) throws ApplicationException {
        boolean isCtaMatriz = false;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("ubi2", codDireccion);
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                String ctaMtz = obj.getString("UBI_CUENTA_MATRIZ");
                if (ctaMtz != null && ctaMtz.equals("1")) {
                    isCtaMatriz = true;
                }
            }
            return isCtaMatriz;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar la cuenta matriz. EX000: " + e.getMessage(), e);
        }
    }

    /**
     * Crea un HHPP partiendo de una solicitud de negocio
     *
     * @param solicitud objeto que contiene toda la información necesaria para
     * crear el HHPP
     * @param nombreFuncionalidad Nombre de la funcionalidad que solicita la
     * creación de HHPP
     * @param nombreArchivo Nombre del archivo para RR en el cual van a ser
     * incluidos los HHPP en RR
     * @param user
     * @return Mensaje informando el resultado de la operación.
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public SolicitudNegocio createHHPP(SolicitudNegocio solicitud, String nombreFuncionalidad, String nombreArchivo, String user) throws ApplicationException {
        try {
            AddressEJB addressEjb = new AddressEJB();
            ConsultaEspecificaManager managerConsultaEspecifica = new ConsultaEspecificaManager();

            Direccion direccion = null;
            SubDireccion subdireccion = null;
            BigDecimal id_hhpp = BigDecimal.ZERO;
            EnvioCorreo enviocorreo = new EnvioCorreo();
            String tipoDireccion = "";
            String direccionsubDireccion = Constant.DIRECCION + Constant.SUB_DIRECCION;
            tipoDireccion = addressEjb.validarTipoDireccion(solicitud.getSonPlaca(), solicitud.getSonServinformacion(), solicitud.getSonComplemento());
            LOGGER.info("At HhppEJB - createHHPP tipoDireccion is:::::" + tipoDireccion);

            if (tipoDireccion.equals(Constant.DIRECCION)) {
                try {
                    LOGGER.info("Before queryAddressOnRepository");
                    direccion = addressEjb.queryAddressOnRepository(solicitud.getSonServinformacion(), null);
                } catch (ApplicationException e) {
                    String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                    LOGGER.error(msg);
                }
            } else if (tipoDireccion.equals(direccionsubDireccion)) {
                direccion = addressEjb.queryAddressOnRepository(solicitud.getSonPlaca(),null);
                subdireccion = addressEjb.querySubAddressOnRepoByCod(solicitud.getSonServinformacion(), null);

            } else if (tipoDireccion.equals(Constant.DIR_INTRADUCIBLE)) {
                direccion = addressEjb.queryAddressOnRepoByDirNoStand(solicitud.getSonNostandar());
            }

            if (direccion != null && (tipoDireccion.equals(Constant.DIRECCION) || tipoDireccion.equals(Constant.DIR_INTRADUCIBLE))) {
                LOGGER.info("Direccion is not null and is " + tipoDireccion + "!!!! Before queryHhppByIdDir:");
                Hhpp hhppExistente = queryHhppByIdDireccion(direccion.getDirId());
                if (hhppExistente != null) {
                    solicitud.setSonEstado(Constant.ESTADO_SON_RECHAZADA);
                    solicitud.setSonUsuarioModificacion(user);
                    solicitud.setSonTipoSolucion(Constant.SON_TIPO_SOL_EXISTENTE);
                    String mensajeCorreo = "Solicitud " + solicitud.getSonEstado() + ", " + solicitud.getSonTipoSolucion()
                            + ", para: " + solicitud.getSonFormatoIgac() + " En la Ciudad:" + solicitud.getGeograficoPolitico().getGpoNombre()
                            + ", y Regional:" + solicitud.getGeograficoPolitico().getGeograficoPolitico().getGpoNombre()
                            + "porque," + solicitud.getSonResGestion() + "." + "\n" + "Cualquier inconveniente comunicarse con: " + solicitud.getSonUsuGestion() + DOMINIO;
                    enviocorreo.envio(solicitud.getSonEmailSolicitante(), solicitud.getSonTipoSolucion(), mensajeCorreo);
                    LOGGER.info("After enviar correo solicitud has:" + solicitud);
                } else {
                    Hhpp hhpp = new Hhpp();
                    List<Marcas> marcas = new ArrayList<>();
                    marcas.add(solicitud.getMarcas());
                    hhpp.setDireccion(direccion);
                    hhpp.setNodo(solicitud.getNodo());
                    hhpp.setTipoHhpp(solicitud.getTipoHhpp());
                    hhpp.setTipoConexionHhpp(solicitud.getTipoHhppConexion());
                    hhpp.setTipoRedHhpp(solicitud.getTipoHhppRed());
                    hhpp.setMarcas(marcas);
                    hhpp.setHhppUsuarioCreacion(user);
                    hhpp.setEstadoHhpp(manager.queryEstadoHhpp(solicitud.getSonEstadoUni()));
                    try {
                        if (hhpp.getDireccion().getDirId() != null) {
                            id_hhpp = persistHhpp(hhpp, nombreFuncionalidad);
                        }
                        LOGGER.info("After persistHhpp with ID:!!!!" + id_hhpp);
                    } catch (ApplicationException e) {
                        String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                        LOGGER.error(msg);
                    }
                    solicitud.setSonEstado(Constant.ESTADO_SON_ARCH_GENERADO);
                    solicitud.setSonArchivoGeneradoRr(nombreArchivo);
                }
            } else if (subdireccion != null && tipoDireccion.equals(direccionsubDireccion)) {
                Hhpp hhppSdi = queryHhppByIdSubDireccion(subdireccion.getSdiId());
                if (hhppSdi != null) {
                    solicitud.setSonEstado(Constant.ESTADO_SON_RECHAZADA);
                    solicitud.setSonUsuarioModificacion(user);
                    solicitud.setSonTipoSolucion(Constant.SON_TIPO_SOL_EXISTENTE);
                    String mensajeCorreo = "Solicitud " + solicitud.getSonEstado() + ", " + solicitud.getSonTipoSolucion()
                            + ", para: " + solicitud.getSonFormatoIgac() + " En la Ciudad:" + solicitud.getGeograficoPolitico().getGpoNombre()
                            + ", y Departamento:" + solicitud.getGeograficoPolitico().getGeograficoPolitico().getGpoNombre()
                            + " rechazada porque," + solicitud.getSonTipoSolucion() + "." + "\n" + " Cualquier inconveniente por favor comunicarse con: " + solicitud.getSonUsuGestion();
                    enviocorreo.envio(solicitud.getSonEmailSolicitante(), solicitud.getSonTipoSolucion(), mensajeCorreo);
                } else {
                    Hhpp hhpp = new Hhpp();
                    List<Marcas> marcas = new ArrayList<>();
                    marcas.add(solicitud.getMarcas());
                    hhpp.setDireccion(subdireccion.getDireccion());
                    hhpp.setSubDireccion(subdireccion);
                    hhpp.setNodo(solicitud.getNodo());
                    hhpp.setTipoHhpp(solicitud.getTipoHhpp());
                    hhpp.setTipoConexionHhpp(solicitud.getTipoHhppConexion());
                    hhpp.setTipoRedHhpp(solicitud.getTipoHhppRed());
                    hhpp.setMarcas(marcas);
                    hhpp.setHhppUsuarioCreacion(user);
                    hhpp.setEstadoHhpp(manager.queryEstadoHhpp(solicitud.getSonEstadoUni()));
                    if (hhpp.getDireccion().getDirId() != null) {
                        id_hhpp = persistHhpp(hhpp, nombreFuncionalidad);
                    }
                    solicitud.setSonEstado(Constant.ESTADO_SON_ARCH_GENERADO);
                    solicitud.setSonArchivoGeneradoRr(nombreArchivo);
                }
            } else if (direccion == null && subdireccion == null && tipoDireccion.equals(direccionsubDireccion)) {
                BigDecimal id_subDir;
                BigDecimal id_dir;
                try {
                    LOGGER.info("Before createAddressFromSolicitudNegocio:");
                    id_dir = addressEjb.saveAddress(solicitud, tipoDireccion, user, nombreFuncionalidad);
                    id_subDir = addressEjb.saveSubAddress(solicitud, user, nombreFuncionalidad, id_dir);
                    LOGGER.info("After createAddressFromSolicitudNegocio id_subDir has:" + id_subDir);
                    if (id_subDir != BigDecimal.ZERO) {
                        subdireccion = managerConsultaEspecifica.querySubAddressOnRepositoryById(id_subDir);
                        LOGGER.info("After createAddressFromSolicitudNegocio subdireccion has:" + subdireccion);
                    }
                } catch (ApplicationException e) {
                    String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                    LOGGER.error(msg);
                }

                Hhpp hhpp = new Hhpp();

                if (subdireccion != null) {
                    List<Marcas> marcas = null;
                    if (solicitud.getMarcas() != null) {
                        marcas = new ArrayList<>();
                        marcas.add(solicitud.getMarcas());
                    }
                    hhpp.setDireccion(subdireccion.getDireccion());
                    hhpp.setSubDireccion(subdireccion);
                    hhpp.setNodo(solicitud.getNodo());
                    hhpp.setTipoHhpp(solicitud.getTipoHhpp());
                    hhpp.setTipoConexionHhpp(solicitud.getTipoHhppConexion());
                    hhpp.setTipoRedHhpp(solicitud.getTipoHhppRed());
                    hhpp.setMarcas(marcas);
                    hhpp.setHhppUsuarioCreacion(user);
                    hhpp.setEstadoHhpp(manager.queryEstadoHhpp(solicitud.getSonEstadoUni()));

                    try {
                        if (hhpp.getDireccion() != null) {
                            id_hhpp = persistHhpp(hhpp, nombreFuncionalidad);
                        }
                        LOGGER.info("After persistHhpp para una Dir q no existe!!:");
                    } catch (ApplicationException e) {
                        String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                        LOGGER.error(msg);
                    }
                    solicitud.setSonEstado(Constant.ESTADO_SON_ARCH_GENERADO);
                    solicitud.setSonArchivoGeneradoRr(nombreArchivo);
                }
            } else if (direccion == null && (tipoDireccion.equals(Constant.DIR_INTRADUCIBLE) || tipoDireccion.equals(Constant.DIRECCION))) {
                BigDecimal dir_id = addressEjb.saveAddress(solicitud, tipoDireccion, user, nombreFuncionalidad);
                if (dir_id != null) {
                    direccion = addressEjb.queryAddressOnRepoById(dir_id.toString());
                }
                if (direccion != null) {
                    Hhpp hhpp = new Hhpp();
                    List<Marcas> marcas = null;
                    if (solicitud.getMarcas() != null) {
                        marcas = new ArrayList<>();
                        marcas.add(solicitud.getMarcas());
                    }

                        hhpp.setDireccion(direccion);
                        hhpp.setNodo(solicitud.getNodo());
                        hhpp.setTipoHhpp(solicitud.getTipoHhpp());
                        hhpp.setTipoConexionHhpp(solicitud.getTipoHhppConexion());
                        hhpp.setTipoRedHhpp(solicitud.getTipoHhppRed());
                        hhpp.setMarcas(marcas);
                        hhpp.setHhppUsuarioCreacion(user);
                        hhpp.setEstadoHhpp(manager.queryEstadoHhpp(solicitud.getSonEstadoUni()));
                        try {
                            if (hhpp.getDireccion().getDirId() != null) {
                                id_hhpp = persistHhpp(hhpp, nombreFuncionalidad);
                            }
                            LOGGER.info("After persistHhpp with ID:!!!!" + id_hhpp);
                        } catch (ApplicationException e) {
                            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                            LOGGER.error(msg);
                        }
                        solicitud.setSonEstado(Constant.ESTADO_SON_ARCH_GENERADO);
                        solicitud.setSonArchivoGeneradoRr(nombreArchivo);
                }
            } else if (direccion != null && subdireccion == null && tipoDireccion.equals(direccionsubDireccion)) {
                Hhpp hhppdir = queryHhppByIdDireccion(direccion.getDirId());
                if (hhppdir != null) {
                    solicitud.setSonEstado(Constant.ESTADO_SON_RECHAZADA);
                    solicitud.setSonUsuarioModificacion(user);
                    solicitud.setSonTipoSolucion(Constant.SON_TIPO_SOL_EXISTENTE);
                    String mensajeCorreo = "Solicitud " + solicitud.getSonEstado() + ", " + solicitud.getSonTipoSolucion()
                            + ", para: " + solicitud.getSonFormatoIgac() + " En la Ciudad:" + solicitud.getGeograficoPolitico().getGpoNombre()
                            + ", y Departamento:" + solicitud.getGeograficoPolitico().getGeograficoPolitico().getGpoNombre()
                            + " rechazada porque," + solicitud.getSonTipoSolucion() + ", esta tratando de adicionar complementos a una casa." + "\n" + " Cualquier inconveniente por favor comunicarse con: " + solicitud.getSonUsuGestion();
                    enviocorreo.envio(solicitud.getSonEmailSolicitante(), solicitud.getSonTipoSolucion(), mensajeCorreo);
                } else {
                    BigDecimal id_subDir;
                    try {
                        LOGGER.info("Before createAddressFromSolicitudNegocio:");
                        id_subDir = addressEjb.saveSubAddress(solicitud, user, nombreFuncionalidad, direccion.getDirId());
                        if (id_subDir != BigDecimal.ZERO) {
                            subdireccion = managerConsultaEspecifica.querySubAddressOnRepositoryById(id_subDir);
                        }
                    } catch (ApplicationException ex) {
                        String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
                        LOGGER.error(msg);
                    }

                    Hhpp hhpp = new Hhpp();

                    if (subdireccion != null) {
                        List<Marcas> marcas = null;
                        if (solicitud.getMarcas() != null) {
                            marcas = new ArrayList<>();
                            marcas.add(solicitud.getMarcas());
                        }
                        hhpp.setDireccion(subdireccion.getDireccion());
                        hhpp.setSubDireccion(subdireccion);
                        hhpp.setNodo(solicitud.getNodo());
                        hhpp.setTipoHhpp(solicitud.getTipoHhpp());
                        hhpp.setTipoConexionHhpp(solicitud.getTipoHhppConexion());

                        hhpp.setTipoRedHhpp(solicitud.getTipoHhppRed());
                        hhpp.setMarcas(marcas);
                        hhpp.setHhppUsuarioCreacion(user);
                        hhpp.setEstadoHhpp(manager.queryEstadoHhpp(solicitud.getSonEstadoUni()));
                        if (hhpp.getDireccion() != null) {
                            id_hhpp = persistHhpp(hhpp, nombreFuncionalidad);
                        }
                        LOGGER.info("After persistHhpp para una Dir q no existe!!:");
                        if (id_hhpp != BigDecimal.ZERO) {
                            solicitud.setSonEstado(Constant.ESTADO_SON_ARCH_GENERADO);
                            solicitud.setSonArchivoGeneradoRr(nombreArchivo);
                        }
                    }
                }
            }
            if (id_hhpp != BigDecimal.ZERO) {
                solicitud.setSonUsuarioModificacion(user);
            }
            LOGGER.info("Before return solicitud  at createHHPP sonUsuarioModificacion has:!!:" + solicitud.getSonUsuarioModificacion());
            LOGGER.info("Before return solicitud  at createHHPP sonEstado has:!!:" + solicitud.getSonEstado());
            LOGGER.info("Before return solicitud  at createHHPP SonArchivoGeneradoRr has:!!:" + solicitud.getSonArchivoGeneradoRr());
            LOGGER.info("Before return solicitud  at createHHPP SontipoHhppRed has:!!:" + solicitud.getTipoHhppRed());
            LOGGER.info("Before return solicitud  at createHHPP !!:" + solicitud);
            return solicitud;
        } catch (ApplicationException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de crear el HHPP. EX000: " + ex.getMessage(), ex);
        }
    }

    /**
     * Consultar un Tipo de Ubicación
     *
     * @param tubNombre Nombre del Tipo de Ubicacion que se quiere consultar
     * @return tipoUbicacion TipoUbicacion que tiene por nombre tubNombre
     */
    private TipoUbicacion queryTipoUbicacion(String tubNombre) throws ApplicationException {
        TipoUbicacion tipoUbicacion = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("tub1", tubNombre);
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                tipoUbicacion = new TipoUbicacion();
                BigDecimal tub_id = obj.getBigDecimal("TUB_ID");
                String tub_nombre = obj.getString("TUB_NOMBRE");
                tipoUbicacion.setTubId(tub_id);
                tipoUbicacion.setTubNombre(tub_nombre);
            }
            return tipoUbicacion;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar el tipo de ubicación. EX000: " + e.getMessage(), e);
        }
    }

    private BigDecimal persistHhpp(Hhpp hhpp, String nombreFuncionalidad) throws ApplicationException {
        HHPPManager manager1 = new HHPPManager();
        return manager1.persistHhpp(hhpp, nombreFuncionalidad);
    }

    /**
     *
     * @param nuevoHhpp
     * @param nombreFuncionalidad
     * @param user
     * @return
     * @throws ExceptionDB
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public String updateHhpp(Hhpp nuevoHhpp, String nombreFuncionalidad, String user) throws ExceptionDB, ApplicationException {
        String msj;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            //En este punto se debe extraer los datos que se desean modificar. p.e: estado
            String idHhpp = "";
            String nuevoEstado = "";
            String nuevoNodo = "";
            try {
                idHhpp = nuevoHhpp.getHhppId().toString();
                nuevoEstado = nuevoHhpp.getEstadoHhpp().getEhhId();
                nuevoNodo = nuevoHhpp.getNodo().getNodId().toString();
            } catch (Exception e) {
                throw new ApplicationException("Error: los datos del hhpp" + nuevoHhpp.getHhppId() + " son incorrectos.");
            }
            boolean ok = false;

            ok = adb.in("hhp8", nuevoEstado, nuevoNodo, user, idHhpp);
            if (ok) {
                AuditoriaEJB audi = new AuditoriaEJB();
                audi.auditar(nombreFuncionalidad, Constant.HHPP, user, Constant.UPDATE, nuevoHhpp.auditoria(), adb);
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
            throw new ApplicationException("Error al momento de actualizar el HHPP. EX000: " + e.getMessage(), e);
        }
    }

    /**
     * Buscar un Hhpp dada una dirección.
     *
     * @param direccionNoStandar
     * @param addressGeodata
     * @return Hhpp al que pertenece la dirección.
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public Hhpp queryHhppByDireccion(String direccionNoStandar, AddressGeodata addressGeodata) throws ApplicationException {
        try {
            Hhpp hhpp = null;
            AddressEJB addressEjb = new AddressEJB();
            Direccion direccion = null;
            SubDireccion subdireccion = null;
            String tipoDireccion = "";
            String direccionsubDireccion = Constant.DIRECCION + Constant.SUB_DIRECCION;
            if (addressGeodata != null) {
                tipoDireccion = addressEjb.validarTipoDireccion(addressGeodata.getCodencont(), addressGeodata.getCoddir(), (addressGeodata.getCoddir()).substring(addressGeodata.getCodencont().length()));
            } else {
                tipoDireccion = Constant.DIR_INTRADUCIBLE;
            }
            LOGGER.info("At HhppEJB - createHHPP tipoDireccion is:::::" + tipoDireccion);
            if (tipoDireccion.equals(Constant.DIRECCION)) {
                try {
                    LOGGER.info("Before queryAddressOnRepository");
                    if (addressGeodata != null) {
                        direccion = addressEjb.queryAddressOnRepository(addressGeodata.getCodencont(), null);
                        if (direccion == null) {
                            direccion = addressEjb.queryAddressOnRepository(addressGeodata.getCoddir(), null);
                        }
                    }
                } catch (ApplicationException e) {
                    LOGGER.error(MESSAGE_ERROR + e.getMessage(), e);
                }
            } else if (tipoDireccion.equals(direccionsubDireccion)) {
                if (addressGeodata != null) {
                    direccion = addressEjb.queryAddressOnRepository(addressGeodata.getCodencont(), null);
                    subdireccion = addressEjb.querySubAddressOnRepoByCod(addressGeodata.getCoddir(), null);
                }
            } else if (tipoDireccion.equals(Constant.DIR_INTRADUCIBLE)) {
                direccion = addressEjb.queryAddressOnRepoByDirNoStand(direccionNoStandar);
            }

            if (direccion != null && (tipoDireccion.equals(Constant.DIRECCION) || tipoDireccion.equals(Constant.DIR_INTRADUCIBLE))) {
                hhpp = queryHhppByIdDireccion(direccion.getDirId());
            } else if (subdireccion != null && tipoDireccion.equals(direccionsubDireccion)) {
                hhpp = queryHhppByIdSubDireccion(subdireccion.getSdiId());
            }
            return hhpp;
        } catch (IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de consultar el HHPP por dirección. EX000: " + ex.getMessage(), ex);
        }
    }

    /**
     * Actualizar el estado de un HHPP
     *
     * @param idHhpp Identificador unico del HHPP al que se le desea modificar
     * el estado
     * @param nuevoEstado Nuevo estado del HHPP
     * @param nombreFuncionalidad Nombre de la funcionalidad que solicita el
     * cambio de estado del HHPP
     * @param user Usuario q solicita el cambio
     * @return true si la operación fue exitosa, false en caso contrario.
     * @throws ExceptionDB
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public boolean updateEstado(BigDecimal idHhpp, String nuevoEstado, String nombreFuncionalidad, String user) throws ExceptionDB, ApplicationException {
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            boolean ok = false;
            ok = adb.in("hhp9", nuevoEstado, user, idHhpp.toString());
            if (ok) {
                AuditoriaEJB audi = new AuditoriaEJB();
                Hhpp nuevoHhpp = queryHhppById(idHhpp.toString());
                audi.auditar(nombreFuncionalidad, Constant.HHPP, user, Constant.UPDATE, nuevoHhpp.auditoria(), adb);

            }
            DireccionUtil.closeConnection(adb);
            return ok;
        } catch (ApplicationException | ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de actualizar el estado del HHPP. EX000: " + e.getMessage(), e);
        }

    }

    /**
     * Actualizar el nodo de un HHPP
     *
     * @param idHhpp Identificador unico del HHPP al que se le desea modificar
     * el estado
     * @param nuevoNodo Nuevo nodo del HHPP
     * @param nombreFuncionalidad Nombre de la funcionalidad que solicita el
     * cambio de nodo del HHPP
     * @param user Usuario q solicita el cambio
     * @return true si la operación fue exitosa, false en caso contrario.
     * @throws ExceptionDB
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public boolean updateNodo(BigDecimal idHhpp, BigDecimal nuevoNodo, String nombreFuncionalidad, String user) throws ExceptionDB, ApplicationException {
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            //En este punto se debe extraer los datos que se desean modificar. p.e: estado
            boolean ok = false;
            ok = adb.in("hhp10", nuevoNodo.toString(), user, idHhpp.toString());
            if (ok) {
                AuditoriaEJB audi = new AuditoriaEJB();
                Hhpp nuevoHhpp = queryHhppById(idHhpp.toString());
                audi.auditar(nombreFuncionalidad, Constant.HHPP, user, Constant.UPDATE, nuevoHhpp.auditoria(), adb);
            }
            DireccionUtil.closeConnection(adb);
            return ok;
        } catch (ApplicationException | ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de actualizar el nodo. EX000: " + e.getMessage(), e);
        }
    }

    /**
     * Actualizar usuario y la fecha de modificación de un HHPP
     *
     * @param idHhpp Identificador unico del HHPP al que se le desea modificar
     * el usuario y la fecha de modificación
     * @param nombreFuncionalidad Nombre de la funcionalidad que solicita el
     * cambio de nodo del HHPP
     * @param user Usuario q solicita el cambio
     * @return true si la operación fue exitosa, false en caso contrario.
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public boolean updateHhppUSerDate(BigDecimal idHhpp, String nombreFuncionalidad, String user) throws ApplicationException {
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            boolean ok = false;
            ok = adb.in("hhp11", user, idHhpp.toString());
            if (ok) {
                AuditoriaEJB audi = new AuditoriaEJB();
                Hhpp nuevoHhpp = queryHhppById(idHhpp.toString());
                audi.auditar(nombreFuncionalidad, Constant.HHPP, user, Constant.UPDATE, nuevoHhpp.auditoria(), adb);
            }
            DireccionUtil.closeConnection(adb);
            return ok;
        } catch (ApplicationException | ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de actualizar información del HHPP. EX000: " + e.getMessage(), e);
        }
    }

    /**
     * Cargar las marcas de un HHPP especifico
     *
     * @param idHhpp Identificador único del HHPP
     * @return lista de blacklist asociados al HHPP indicado.
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<Marcas> queryMarcasHhppByIdHhpp(String idHhpp) throws ApplicationException {
        //SELECT MHH.MHH_ID , MHH.MAR_ID, MAR.MAR_CODIGO, MAR.MAR_NOMBRE FROM MARCAS_HHPP MHH, MARCAS MAR  WHERE HHP_ID =? AND MHH.MAR_ID = MAR.MAR_ID
        List<Marcas> marcas = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("mhh4", idHhpp);
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                marcas = new ArrayList<>();
                for (DataObject obj : list.getList()) {
                    Marcas marca = new Marcas();
                    BigDecimal id_marca = obj.getBigDecimal("MAR_ID");
                    String mar_codigo = obj.getString("MAR_CODIGO");
                    String mar_nombre = obj.getString("MAR_NOMBRE");
                    marca.setMarId(id_marca);
                    marca.setMarCodigo(mar_codigo);
                    marca.setMarNombre(mar_nombre);
                    marcas.add(marca);
                }
            }
            return marcas;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar las marcas del HHPP. EX000: " + e.getMessage(), e);
        }
    }

    /**
     * Crea un HHPP partiendo de un detalle solicitud
     *
     * @param detalle objeto que contiene toda la información necesaria para
     * crear el HHPP
     * @param nombreFuncionalidad Nombre de la funcionalidad que solicita la
     * creación de HHPP
     * @param user
     * @return Detalle de solicitud actualizado
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public DetalleSolicitud createHHPPfromRed(DetalleSolicitud detalle, String nombreFuncionalidad, String user) throws ApplicationException {
        try {
            AddressEJB addressEjb = new AddressEJB();
            return detalle;
        } catch (IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de crear el HHPP por red. EX000: " + ex.getMessage(), ex);
        }
    }

    /**
     *
     * @return
     */
    @Override
    public boolean updateEstadosRR() {
        try {
            return uploadFile();
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            return false;
        }

    }

    /**
     * Funcion usada para actualizar los Hhpp en Mgl apartir de la informacion
     * consultada en RR
     * @throws ApplicationException
     *
     * @Author Orlando Velasquez
     */
    @Override
    public void updateHhppMglFromRR() throws ApplicationException {        
        String nomServer = "";
        try {
            //JDHT
            nomServer = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException ex) {
            String msgError = "Error en " + ClassUtils.getCurrentMethodName(this.getClass()) + ": " + ex.getMessage();
            LOGGER.error(msgError);
        }
        MglParametrosTrabajos mglParametrosTrabajos
                = mglParametrosTrabajosManager.findMglParametrosTrabajosByName(
                        co.com.claro.mgl.utils.Constant.TAREAS_PROGRAMADAS_HHPP_UPDATE, nomServer);
        if (mglParametrosTrabajos==null) {
            return;
        }
        ArrayList<MglParametrosTrabajos> mglParametrosTrabajosList = 
                mglParametrosTrabajosManager.findMglParametrosTrabajosByName(
                        co.com.claro.mgl.utils.Constant.TAREAS_PROGRAMADAS_HHPP_UPDATE);
        
        if (mglParametrosTrabajosList != null) {
            for ( MglParametrosTrabajos temporal:mglParametrosTrabajosList){
                if (temporal.getEstado().getIdentificadorInternoApp().
                    equalsIgnoreCase(co.com.claro.mgl.utils.Constant.ESTADO_EJECUTANDOSE_TAREAS_PROGRAMADAS)){
                    /** 
                     * si encuentra algun otro servidor parametrizado ya ejecutando la tarea.
                     */
                    return;
                }
            }
        }
        
        //validar que no exista otra tarea programada del mismo tipo en ejecucion
        mglParametrosTrabajos = modificarEstadoTareaProgramada(
                    mglParametrosTrabajos, co.com.claro.mgl.utils.Constant.ESTADO_EJECUTANDOSE_TAREAS_PROGRAMADAS);
        MglParametrosTrabajosDetail mglParametrosTrabajosDetail = crearDetalleTareaProgramada(mglParametrosTrabajos,
                co.com.claro.mgl.utils.Constant.ESTADO_EJECUTANDOSE_DETALLE_TAREAS_PROGRAMADAS);
        try {    
            HhppPaginationResponse hhppPaginationResponse = consultarIdsHhppModificadosEnRR();
            if (hhppPaginationResponse != null) {
                //Actualizacion del Hhpp con la informacion de RR
                actualizarHhppConDatosRR(hhppPaginationResponse);
                mglParametrosTrabajosDetail.setFechaFin(new Date());
                mglParametrosTrabajosDetail.setRegistrosAfectados(Long.valueOf(hhppPaginationResponse.getArrunky().size()));
                modificarEstadoDetalleTareaProgramada(mglParametrosTrabajosDetail,
                        co.com.claro.mgl.utils.Constant.ESTADO_FINALIZADA_CON_EXITO_DETALLE_TAREAS_PROGRAMADAS);
            } else {
                modificarEstadoDetalleTareaProgramada(mglParametrosTrabajosDetail,
                        co.com.claro.mgl.utils.Constant.ESTADO_FINALIZADA_CON_ERROR_DETALLE_TAREAS_PROGRAMADAS);
            }
        } catch (ApplicationException | ParseException ex) {
            modificarEstadoDetalleTareaProgramada(mglParametrosTrabajosDetail,
                        co.com.claro.mgl.utils.Constant.ESTADO_FINALIZADA_CON_ERROR_DETALLE_TAREAS_PROGRAMADAS);
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
        } finally{
            try{
                modificarEstadoTareaProgramada(
                    mglParametrosTrabajos, co.com.claro.mgl.utils.Constant.ESTADO_FINALIZADA_TAREAS_PROGRAMADAS);
            }catch( ApplicationException ex ){
                String msg = "Se produjo un error al momento de ejecutar el método '"
                        + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage()
                        +" cambiando el estado de la tarea programada....paginacion de hhpp.";
                LOGGER.error(msg);
            }
        }
    }
    /**
     * Metodo usado para actualizar los Hhpp en MER apartir de la informacion
     * consultada en RR
     * @throws ApplicationException
     *
     * @Author Johnnatan Ortiz
     */
    @Override
    public void updateHhppFromRR() throws ApplicationException {        
        
        
        ArrayList<MglParametrosTrabajos> mglParametrosTrabajoList = 
                mglParametrosTrabajosManager.findMglParametrosTrabajosByName(
                        co.com.claro.mgl.utils.Constant.CONF_TASK_HHPP_UPDATE);
        //Validamos que si existe un proceso en ejecucion
        boolean isTaskExecutioNow = false;
        if (mglParametrosTrabajoList != null 
                && !mglParametrosTrabajoList.isEmpty()) {
            for ( MglParametrosTrabajos taskExecution : mglParametrosTrabajoList){
                if (taskExecution.getEstado() != null
                        && taskExecution.getEstado().getIdentificadorInternoApp() != null
                        && taskExecution.getEstado().getIdentificadorInternoApp().
                    equalsIgnoreCase(co.com.claro.mgl.utils.Constant.ESTADO_EJECUTANDOSE_TAREAS_PROGRAMADAS)){
                    //encuentra que en algun otro servidor parametrizado 
                    // ya se ejecutando la tarea.
                    isTaskExecutioNow = true;
                }
            }
        }
        // Si no hay una tarea ejecutandose en el momento se lanza la ejecucion 
        // de la  actualizacion 
        if (!isTaskExecutioNow) {            
            MglParametrosTrabajos mglParametrosTrabajos = mglParametrosTrabajoList.get(0);
            //Actualizamos el registro para indicar que se ejecutara una tarea
            mglParametrosTrabajos = modificarEstadoTareaProgramada(
                    mglParametrosTrabajos, 
                    co.com.claro.mgl.utils.Constant.ESTADO_EJECUTANDOSE_TAREAS_PROGRAMADAS);
            //Se crea el detalle de la tarea
            MglParametrosTrabajosDetail mglParametrosTrabajosDetail = 
                    crearDetalleTareaProgramada(mglParametrosTrabajos,
                    co.com.claro.mgl.utils.Constant.ESTADO_EJECUTANDOSE_DETALLE_TAREAS_PROGRAMADAS);
            try {
                //Se realiza la actualizacion de los HHPP
                HhppPaginationResponse hhppPaginationResponse = consultarIdsHhppModificadosEnRR();
                if (hhppPaginationResponse != null) {
                    //Actualizacion del Hhpp con la informacion de RR
                    actualizarHhppConDatosRR(hhppPaginationResponse);
                    mglParametrosTrabajosDetail.setFechaFin(new Date());
                    mglParametrosTrabajosDetail.setRegistrosAfectados(
                            Long.valueOf(hhppPaginationResponse.getArrunky().size()));
                    modificarEstadoDetalleTareaProgramada(mglParametrosTrabajosDetail,
                            co.com.claro.mgl.utils.Constant.ESTADO_FINALIZADA_CON_EXITO_DETALLE_TAREAS_PROGRAMADAS);
                } else {
                    modificarEstadoDetalleTareaProgramada(mglParametrosTrabajosDetail,
                            co.com.claro.mgl.utils.Constant.ESTADO_FINALIZADA_CON_ERROR_DETALLE_TAREAS_PROGRAMADAS);
                }
            } catch (ApplicationException | ParseException ex) {
                modificarEstadoDetalleTareaProgramada(mglParametrosTrabajosDetail,
                        co.com.claro.mgl.utils.Constant.ESTADO_FINALIZADA_CON_ERROR_DETALLE_TAREAS_PROGRAMADAS);
                String msg = "Se produjo un error al momento de ejecutar el método '"
                        + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
                LOGGER.error(msg);
            } finally {
                try {
                    modificarEstadoTareaProgramada(
                            mglParametrosTrabajos, co.com.claro.mgl.utils.Constant.ESTADO_FINALIZADA_TAREAS_PROGRAMADAS);
                } catch (ApplicationException ex) {
                    String msg = "Se produjo un error al momento de ejecutar el método '"
                            + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage()
                            + " cambiando el estado de la tarea programada....paginacion de hhpp.";
                    LOGGER.error(msg);
                }
            }
        }
        
    }
      /**
     * Metodo usado para actualizar los Hhpp en MER apartir de la informacion
     * consultada en RR de la tabla de auditoria
     * @throws ApplicationException
     *
     * @Author Victor Bocanegra
     */
    @Override
    public void updateHhppMERFromRR() throws ApplicationException {        
        
        
        ArrayList<MglParametrosTrabajos> mglParametrosTrabajoList = 
                mglParametrosTrabajosManager.findMglParametrosTrabajosByName(
                        co.com.claro.mgl.utils.Constant.CONF_TASK_HHPP_UPDATE);

        //Validamos que si existe un proceso en ejecucion
        boolean isTaskExecutioNow = false;
        if (mglParametrosTrabajoList != null 
                && !mglParametrosTrabajoList.isEmpty()) {
            for ( MglParametrosTrabajos taskExecution : mglParametrosTrabajoList){
                if (taskExecution.getEstado() != null
                        && taskExecution.getEstado().getIdentificadorInternoApp() != null
                        && taskExecution.getEstado().getIdentificadorInternoApp().
                    equalsIgnoreCase(co.com.claro.mgl.utils.Constant.ESTADO_EJECUTANDOSE_TAREAS_PROGRAMADAS)){
                    //encuentra que en algun otro servidor parametrizado 
                    // ya se ejecutando la tarea.
                    isTaskExecutioNow = true;
                }
            }
        }
        // Si no hay una tarea ejecutandose en el momento se lanza la ejecucion 
        // de la  actualizacion 
        if (!isTaskExecutioNow) {            
            MglParametrosTrabajos mglParametrosTrabajos = mglParametrosTrabajoList.get(0);
            //Actualizamos el registro para indicar que se ejecutara una tarea
            mglParametrosTrabajos = modificarEstadoTareaProgramada(
                    mglParametrosTrabajos, 
                    co.com.claro.mgl.utils.Constant.ESTADO_EJECUTANDOSE_TAREAS_PROGRAMADAS);
            //Se crea el detalle de la tarea
            MglParametrosTrabajosDetail mglParametrosTrabajosDetail = 
                    crearDetalleTareaProgramada(mglParametrosTrabajos,
                    co.com.claro.mgl.utils.Constant.ESTADO_EJECUTANDOSE_DETALLE_TAREAS_PROGRAMADAS);
            try {
                //Se realiza la consulta a la tabla de auditoria en RR y posterior guardado en MER
                  if(consultarInfoHhppModificadosEnRR()){
                 //Todo salio bien se procede a consultar los registros em MER para porterior actualizacion  
                    Long registrosMod= actualizarHhppConDatosRRMER();
                    mglParametrosTrabajosDetail.setFechaFin(new Date());
                    mglParametrosTrabajosDetail.setRegistrosAfectados(registrosMod);
                    modificarEstadoDetalleTareaProgramada(mglParametrosTrabajosDetail,
                            co.com.claro.mgl.utils.Constant.ESTADO_FINALIZADA_CON_EXITO_DETALLE_TAREAS_PROGRAMADAS);
                    //Se realiza el llamado al procedimiento que trunca la tabla
                    hhppMglLogRrManager.truncateTable();
                  }else{
                      modificarEstadoDetalleTareaProgramada(mglParametrosTrabajosDetail,
                            co.com.claro.mgl.utils.Constant.ESTADO_FINALIZADA_CON_ERROR_DETALLE_TAREAS_PROGRAMADAS);  
                  }
            } catch (ApplicationException | ParseException ex) {
                modificarEstadoDetalleTareaProgramada(mglParametrosTrabajosDetail,
                        co.com.claro.mgl.utils.Constant.ESTADO_FINALIZADA_CON_ERROR_DETALLE_TAREAS_PROGRAMADAS);
                String msg = "Se produjo un error al momento de ejecutar el método '"
                        + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
                LOGGER.error(msg);
            } finally {
                try {
                    modificarEstadoTareaProgramada(
                            mglParametrosTrabajos, co.com.claro.mgl.utils.Constant.ESTADO_FINALIZADA_TAREAS_PROGRAMADAS);
                } catch (ApplicationException ex) {
                    String msg = "Se produjo un error al momento de ejecutar el método '"
                            + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage()
                            + " cambiando el estado de la tarea programada....paginacion de hhpp.";
                    LOGGER.error(msg);
                }
            }
        }
        
    }
    /**
     * Funcion usada para crear el detalle de la tarea programada en estado
     * ejecutandose
     *
     * @param mglParametrosTrabajos tipo de tarea programada
     * @param estado estado con el cual se crea el detalle de la tarea
     * programada
     * @throws ApplicationException
     * @Author Orlando Velasquez
     */
    private MglParametrosTrabajosDetail crearDetalleTareaProgramada(
            MglParametrosTrabajos mglParametrosTrabajos, String estado) throws ApplicationException {

        MglParametrosTrabajosDetail mglParametrosTrabajosDetail = new MglParametrosTrabajosDetail();
        mglParametrosTrabajosDetail.setFechaInicio(new Date());
        CmtBasicaMgl estadoEnEjecucion = cmtBasicaMglManager.findByCodigoInternoApp(estado);
        mglParametrosTrabajosDetail.setEstado(estadoEnEjecucion);
        mglParametrosTrabajosDetail.setParametrosTrabajosId(mglParametrosTrabajos);
        return mglParametrosTrabajosDetailManager.createDetalleTareaProgramada(mglParametrosTrabajosDetail);
    }

    /**
     * Funcion usada para actualizar el estado de la tarea programada
     *
     * @param mglParametrosTrabajos tipo de tarea programada
     * @param nuevoEstado nuevo estado de la tarea programada
     * @Author Orlando Velasquez
     */
    private MglParametrosTrabajos modificarEstadoTareaProgramada(
            MglParametrosTrabajos mglParametrosTrabajos,
            String nuevoEstado) throws ApplicationException {

        CmtBasicaMgl estado = cmtBasicaMglManager.findByCodigoInternoApp(nuevoEstado);
        mglParametrosTrabajos.setEstado(estado);
        return mglParametrosTrabajosManager.updateMglParametrosTrabajos(mglParametrosTrabajos);
    }

    /**
     * Funcion usada para actualizar el estado del detalle de la tarea
     * programada
     *
     * @param mglParametrosTrabajosDetail detalle de la tarea programada
     * @param nuevoEstado nuevo estado del detalle de la tarea programada
     *
     * @Author Orlando Velasquez
     */
    private void modificarEstadoDetalleTareaProgramada(
            MglParametrosTrabajosDetail mglParametrosTrabajosDetail,
            String nuevoEstado) throws ApplicationException {

        CmtBasicaMgl estado = cmtBasicaMglManager.findByCodigoInternoApp(nuevoEstado);
        mglParametrosTrabajosDetail.setEstado(estado);
        mglParametrosTrabajosDetailManager.updateDetalleTareaProgramada(mglParametrosTrabajosDetail);
    }

    /**
     * Funcion usada para actualizar los datos del Hhpp on los datos que se
     * obtienen de RR
     *
     * @param mglParametrosTrabajos tipo de tarea programada
     * @param estado estado con el cual se crea el detalle de la tarea
     * programada
     * @throws ApplicationException
     * @Author Orlando Velasquez
     */
    private void actualizarHhppConDatosRR(HhppPaginationResponse hhppPaginationResponse)
            throws ApplicationException, ParseException {
        List<HhppMgl> hhppList;

        if(hhppPaginationResponse.getArrunky() != null && !hhppPaginationResponse.getArrunky().isEmpty()){
        for (HhppId hhppId : hhppPaginationResponse.getArrunky()) {
            String paso = "";
            DireccionRRManager direccionRRManager = new DireccionRRManager(true);
            hhppList = hhppMglManager.findHhppByIdRR(hhppId.getHhppId());
            //Validar Existencia del Hhpp en MGL
            if (hhppList != null && !hhppList.isEmpty()) {
                try{
                    HhppMgl hhpp = hhppList.get(0);
                    paso = " obteniendo la informacion actualizada.";
                    HhppResponseQueryRegularUnitRR hhppResponseQueryRegularUnitRR
                            = direccionRRManager.getAllHhppInfoByIdRR(hhppId.getHhppId());
                    //Validamos que no entregue error la consulta del HHPP en RR
                    if (hhppResponseQueryRegularUnitRR.getTipoMensaje() !=null 
                            && !hhppResponseQueryRegularUnitRR.getTipoMensaje().trim().isEmpty()
                            && !hhppResponseQueryRegularUnitRR.getTipoMensaje().trim().equalsIgnoreCase("E")){
                        //Armar fecha de auditoria
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyymmdd");
                        paso = " obteniendo fecha de auditoria.";
                        hhpp.setHhpFechaAudit(sdf.parse(hhppResponseQueryRegularUnitRR.getFechaAuditoria()));
                        paso = " obteniendo comunidad.";
                        hhpp.setHhpComunidad(hhppResponseQueryRegularUnitRR.getComunidad());
                        paso = " obteniendo division.";
                        hhpp.setHhpDivision(hhppResponseQueryRegularUnitRR.getDivision());
                        paso = " obteniendo vendedor.";
                        hhpp.setHhpVendedor(hhppResponseQueryRegularUnitRR.getVendedor());
                        paso = " obteniendo tipo acometida.";
                        hhpp.setHhpTipoAcomet(hhppResponseQueryRegularUnitRR.getTipoAcometida());
                        paso = " obteniendo tipo cable acometida.";
                        hhpp.setHhpTipoCblAcometida(hhppResponseQueryRegularUnitRR.getTipoCblAcometida());
                        paso = " obteniendo head end. ";
                        hhpp.setHhpHeadEnd(hhppResponseQueryRegularUnitRR.getHeadEnd());
                        paso = " obteniendo tipo hhpp.";
                        hhpp.setHhpTipo(hhppResponseQueryRegularUnitRR.getTipo());
                        paso = " obteniendo codigo postal.";
                        hhpp.setHhpCodigoPostal(hhppResponseQueryRegularUnitRR.getCodigoPostal());
                        paso = " obteniendo tipo tecnologia habilitada";
                        hhpp.setThhId(hhppResponseQueryRegularUnitRR.getTipoTecnologiaHabilitada());
                        paso = " obteniendo nodo.";
                        NodoMgl nodo = nodoMglManager.findByCodigo(hhppResponseQueryRegularUnitRR.getNodo());
                        if (nodo != null) {
                            hhpp.setNodId(nodo);
                        }
                        paso = " obteniendo ultima ubicacion.";
                        hhpp.setHhpUltUbicacion(hhppResponseQueryRegularUnitRR.getUltimaUbicacion());
                        if (hhpp.getDireccionObj()!=null){
                            paso = " obteniendo estrado direccion.";
                            hhpp.getDireccionObj().setDirEstrato(BigDecimal.valueOf(
                                    Long.valueOf(hhppResponseQueryRegularUnitRR.getEstrato())));
                        }
                        if(hhpp.getSubDireccionObj()!=null){
                            paso = " obteniendo estrato sub-direccion.";
                            hhpp.getSubDireccionObj().setSdiEstrato(BigDecimal.valueOf(
                                    Long.valueOf(hhppResponseQueryRegularUnitRR.getEstrato())));
                        }
                        hhppMglManager.update(hhpp);
                    } else {
                        String errorProceso = "Error:Masivo Actuializacion de hhpp desde RR:"
                        + ClassUtils.getCurrentMethodName(this.getClass())+": intentando"
                        + " realizar actualizacion id_hhpp: "+hhppId.getHhppId()+" paso: "+paso                       
                        + " descripcion: " + hhppResponseQueryRegularUnitRR.getTipoMensaje();
                    LOGGER.error(errorProceso);
                    }
                }catch( ApplicationException | NumberFormatException | ParseException e ){
                    String errorProceso = "Error:Masivo Actuializacion de hhpp desde RR:"
                        + ClassUtils.getCurrentMethodName(this.getClass())+": intentando"
                        + " realizar actualizacion id_hhpp: "+hhppId.getHhppId()+" paso: "+paso                       
                        + " descripcion: " + e.getMessage();
                    LOGGER.error(errorProceso);
                }
            }else{
                String errorProceso = "Error:Masivo Actuializacion de hhpp desde RR:"
                        + ClassUtils.getCurrentMethodName(this.getClass())
                        + "home pass no existe id_hhpp: "+hhppId.getHhppId();
                LOGGER.error(errorProceso);
            }
        }
      }

    }
    
      /**
     * Funcion usada para actualizar los datos del Hhpp con los datos que se
     * obtienen de RR
     *
     * @throws ApplicationException
     * @Author Victor Bocanegra
     */
    private Long actualizarHhppConDatosRRMER()
            throws ApplicationException, ParseException {

        List<HhppMgl> hhppList;

        //Se realiza el conteo de  los registros
        int expLonPag = 10000;
        PaginacionDto<HhppMglLogRr> paginacionDto = hhppMglLogRrManager.findAllPaginado(0, expLonPag, true);
        long totalPag = paginacionDto.getNumPaginas();

        if (totalPag > 0) {
          
            for (int exPagina = 1; exPagina <= ((totalPag / expLonPag)
                    + (totalPag % expLonPag != 0 ? 1 : 0)); exPagina++) {

                PaginacionDto<HhppMglLogRr> consultaPaginacionDto
                        = hhppMglLogRrManager.findAllPaginado(exPagina, expLonPag, false);

                if (consultaPaginacionDto != null
                        && consultaPaginacionDto.getListResultado() != null
                        && !consultaPaginacionDto.getListResultado().isEmpty()) {

                    for (HhppMglLogRr hhppId : consultaPaginacionDto.getListResultado()) {

                        String paso = "";
                        HhppMgl hhpp = null;
                        hhppList = hhppMglManager.findHhppByIdRR(hhppId.getIdRR().toString());
                        //Validar Existencia del Hhpp en MGL
                        if (hhppList != null && !hhppList.isEmpty()) {
                            try {
                                hhpp = hhppList.get(0);
                                //Armar fecha de auditoria
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyymmdd");
                                paso = " obteniendo fecha de auditoria.";
                                String año;
                                String mes;
                                String dia;
                                if (hhppId.getFechaAudLogAño().toString().length() == 1) {
                                    año = "0" + hhppId.getFechaAudLogAño().toString();
                                } else {
                                    año = hhppId.getFechaAudLogAño().toString();
                                }

                                if (hhppId.getFechaAudLogMes().toString().length() == 1) {
                                    mes = "0" + hhppId.getFechaAudLogMes().toString();
                                } else {
                                    mes = hhppId.getFechaAudLogMes().toString();
                                }

                                if (hhppId.getFechaAudLogDia().toString().length() == 1) {
                                    dia = "0" + hhppId.getFechaAudLogDia().toString();
                                } else {
                                    dia = hhppId.getFechaAudLogDia().toString();
                                }

                                String fechaAud = hhppId.getFechaAudLogSiglo().toString() + año + mes + dia;

                                hhpp.setHhpFechaAudit(sdf.parse(fechaAud));
                                paso = " obteniendo comunidad.";
                                hhpp.setHhpComunidad(hhppId.getComunidad());
                                paso = " obteniendo division.";
                                hhpp.setHhpDivision(hhppId.getDivision());
                                paso = " obteniendo vendedor.";
                                hhpp.setHhpVendedor(hhppId.getVendedor());
                                paso = " obteniendo tipo acometida.";
                                hhpp.setHhpTipoAcomet(hhppId.getTipoAcometida());
                                paso = " obteniendo tipo cable acometida.";
                                hhpp.setHhpTipoCblAcometida(hhppId.getTipoCable());
                                paso = " obteniendo head end. ";
                                hhpp.setHhpHeadEnd(hhppId.getHeadEnd());
                                paso = " obteniendo tipo hhpp.";
                                hhpp.setHhpTipo(hhppId.getTipoHhpp());
                                paso = " obteniendo codigo postal.";
                                hhpp.setHhpCodigoPostal(hhppId.getCodigoPostal());
                                paso = " obteniendo tipo tecnologia habilitada";
                                //hhpp.setThhId(hhppResponseQueryRegularUnitRR.getTipoTecnologiaHabilitada());
                                paso = " obteniendo nodo.";
                                NodoMgl nodo = nodoMglManager.findByCodigo(hhppId.getNodo());
                                if (nodo != null) {
                                    hhpp.setNodId(nodo);
                                }
                                paso = " obteniendo ultima ubicacion.";
                                hhpp.setHhpUltUbicacion(hhppId.getUltimaUbicacion());
                                if (hhpp.getDireccionObj() != null) {
                                    paso = " obteniendo estrado direccion.";
                                    hhpp.getDireccionObj().setDirEstrato(BigDecimal.valueOf(
                                            Long.valueOf(hhppId.getEstrato())));
                                }
                                if (hhpp.getSubDireccionObj() != null) {
                                    paso = " obteniendo estrato sub-direccion.";
                                    hhpp.getSubDireccionObj().setSdiEstrato(BigDecimal.valueOf(
                                            Long.valueOf(hhppId.getEstrato())));
                                }
                                hhppMglManager.updateHhppMgl(hhpp, "SYNCRR", 999);

                            } catch (ApplicationException | NumberFormatException e) {

                                if (hhpp != null) {
                                    String errorProceso = "Error:Masivo Actuializacion de hhpp desde RR:"
                                            + ClassUtils.getCurrentMethodName(this.getClass()) + ": intentando"
                                            + " realizar actualizacion id_hhpp: " + hhpp.getHhpId() + " paso: " + paso
                                            + " descripcion: " + e.getMessage();
                                    LOGGER.error(errorProceso);
                                } else {
                                    String errorProceso = "Error:Masivo Actuializacion de hhpp desde RR:"
                                            + ClassUtils.getCurrentMethodName(this.getClass()) + ": intentando"
                                            + " realizar actualizacion:  paso: " + paso
                                            + " descripcion: " + e.getMessage();
                                    LOGGER.error(errorProceso);
                                }
                            }
                        }
                    }

                }
                consultaPaginacionDto=null;
                System.gc();
            }
        }
        return totalPag;
    }
    /**
     * Funcion usada para consultar en un rango de fechas los Ids de los Hhpp
     * modificados en RR y actualizarlos en MGL
     *
     * @Author Orlando Velasquez
     */
    private HhppPaginationResponse consultarIdsHhppModificadosEnRR() throws ApplicationException {
        HhppPaginationManager hhppPaginationManager = new HhppPaginationManager();
        HhppPaginationRequestDto hhppPaginationRequestDto = new HhppPaginationRequestDto();
        //primera vez que se ejecuta y no existen registro de procesos pasados ejecutados.
        if (mglParametrosTrabajosDetailManager.contarNumeroDeRegistrosDetalleTareaProgramada() == 0) {
            hhppPaginationRequestDto.setFechaInicio(buscarParametroEnMglParametros(
                    co.com.claro.mgl.utils.Constant.FECHA_INICIO_TAREA_PROGRAMADA_HHPP_UPDATE).getValor());
            hhppPaginationRequestDto.setHoraInicio(buscarParametroEnMglParametros(
                    co.com.claro.mgl.utils.Constant.HORA_INICIO_TAREA_PROGRAMADA_HHPP_UPDATE).getValor());
            hhppPaginationRequestDto.setFechaFin(buscarParametroEnMglParametros(
                    co.com.claro.mgl.utils.Constant.FECHA_FIN_TAREA_PROGRAMADA_HHPP_UPDATE).getValor());
            hhppPaginationRequestDto.setHoraFin(buscarParametroEnMglParametros(
                    co.com.claro.mgl.utils.Constant.HORA_FIN_TAREA_PROGRAMADA_HHPP_UPDATE).getValor());
        } else {
            CmtBasicaMgl estado = cmtBasicaMglManager.findByCodigoInternoApp(
                co.com.claro.mgl.utils.Constant.ESTADO_FINALIZADA_CON_EXITO_DETALLE_TAREAS_PROGRAMADAS);
            MglParametrosTrabajosDetail mglParametrosTrabajosDetail
                    = mglParametrosTrabajosDetailManager.findUltimoRegistroDetalleTareaProgramada(estado);
            //si existen una ultima ejecución exitosa, arranca desde esa fecha de inicio el nuevo proceso de sincronización
            if (mglParametrosTrabajosDetail != null) {
                SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd");
                SimpleDateFormat formatoHora = new SimpleDateFormat("HHmmss");
                hhppPaginationRequestDto.setFechaInicio(
                        formatoFecha.format(mglParametrosTrabajosDetail.getFechaInicio()));
                hhppPaginationRequestDto.setHoraInicio(
                        formatoHora.format(mglParametrosTrabajosDetail.getFechaInicio()));
                hhppPaginationRequestDto.setFechaFin(formatoFecha.format(new Date()));
                hhppPaginationRequestDto.setHoraFin(formatoHora.format(new Date()));
            } else {
                //Si no existe ningun registro de proceso exitoso anteriormente ejecutado (primera vez posiblemente)
                hhppPaginationRequestDto.setFechaInicio(buscarParametroEnMglParametros(
                        co.com.claro.mgl.utils.Constant.FECHA_INICIO_TAREA_PROGRAMADA_HHPP_UPDATE).getValor());
                hhppPaginationRequestDto.setHoraInicio(buscarParametroEnMglParametros(
                        co.com.claro.mgl.utils.Constant.HORA_INICIO_TAREA_PROGRAMADA_HHPP_UPDATE).getValor());
                hhppPaginationRequestDto.setFechaFin(buscarParametroEnMglParametros(
                        co.com.claro.mgl.utils.Constant.FECHA_FIN_TAREA_PROGRAMADA_HHPP_UPDATE).getValor());
                hhppPaginationRequestDto.setHoraFin(buscarParametroEnMglParametros(
                        co.com.claro.mgl.utils.Constant.HORA_FIN_TAREA_PROGRAMADA_HHPP_UPDATE).getValor());
            }
        }
        hhppPaginationRequestDto.setCantidadRegistrosSolicitados(buscarParametroEnMglParametros(
                co.com.claro.mgl.utils.Constant.CANTIDAD_REGISTROS_SOLICITADOS_POR_PAGINA_HHPP_UPDATE).getValor());
        return hhppPaginationManager.findHhppRRPagination(hhppPaginationRequestDto, "SEGURAJC");
    }
    
    /**
     * Funcion usada para consultar en un rango de fechas la informacion de los
     * Hhpp modificados en RR y actualizarlos en MGL
     *
     * @Author Victor Bocanegra
     */
    private boolean consultarInfoHhppModificadosEnRR() throws ApplicationException, ParseException {

        boolean result;
        As400Manager as400Manager = new As400Manager();
        String fechaInicioParameterDetail;
        String horaInicioParameterDetail;

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date fechaIniFin;

        //primera vez que se ejecuta y no existen registro de procesos pasados ejecutados.
        if (mglParametrosTrabajosDetailManager.contarNumeroDeRegistrosDetalleTareaProgramada() == 0) {
            fechaInicioParameterDetail = buscarParametroEnMglParametros(
                    co.com.claro.mgl.utils.Constant.FECHA_INICIO_TAREA_PROGRAMADA_HHPP_UPDATE).getValor();
            fechaIniFin = format.parse(fechaInicioParameterDetail);
            horaInicioParameterDetail = buscarParametroEnMglParametros(
                    co.com.claro.mgl.utils.Constant.HORA_INICIO_TAREA_PROGRAMADA_HHPP_UPDATE).getValor();
        } else {
            CmtBasicaMgl estado = cmtBasicaMglManager.findByCodigoInternoApp(
                    co.com.claro.mgl.utils.Constant.ESTADO_FINALIZADA_CON_EXITO_DETALLE_TAREAS_PROGRAMADAS);
            MglParametrosTrabajosDetail mglParametrosTrabajosDetail
                    = mglParametrosTrabajosDetailManager.findUltimoRegistroDetalleTareaProgramada(estado);
            //si existen una ultima ejecución exitosa, arranca desde esa fecha de inicio el nuevo proceso de sincronización

            if (mglParametrosTrabajosDetail != null) {
                SimpleDateFormat formatoFechaFt = new SimpleDateFormat("yyyyMMdd");
                SimpleDateFormat formatoHora = new SimpleDateFormat("HHmmss");
                fechaInicioParameterDetail = formatoFechaFt.format(mglParametrosTrabajosDetail.getFechaInicio());
                fechaIniFin = mglParametrosTrabajosDetail.getFechaInicio();
                horaInicioParameterDetail = formatoHora.format(mglParametrosTrabajosDetail.getFechaInicio());
            } else {
                //Si no existe ningun registro de proceso exitoso anteriormente ejecutado (primera vez posiblemente)
                fechaInicioParameterDetail = buscarParametroEnMglParametros(
                        co.com.claro.mgl.utils.Constant.FECHA_INICIO_TAREA_PROGRAMADA_HHPP_UPDATE).getValor();
                fechaIniFin = format.parse(fechaInicioParameterDetail);
                horaInicioParameterDetail = buscarParametroEnMglParametros(
                        co.com.claro.mgl.utils.Constant.HORA_INICIO_TAREA_PROGRAMADA_HHPP_UPDATE).getValor();
            }
        }

        //Se realiza el conteo de  los registros
        int expLonPag = 10000;
        PaginacionDto<HhppRR> paginacionDto = as400Manager.findAllPaginado(0, expLonPag, fechaIniFin, fechaInicioParameterDetail, horaInicioParameterDetail, true);
        long totalPag = paginacionDto.getNumPaginas();

        if (totalPag > 0) {
            int con = 0;

            for (int exPagina = 1; exPagina <= ((totalPag / expLonPag) + 
                    (totalPag % expLonPag != 0 ? 1 : 0)); exPagina++) {

                PaginacionDto<HhppRR> consultaPaginacionDto
                        = as400Manager.findAllPaginado(exPagina, expLonPag,
                                fechaIniFin, fechaInicioParameterDetail, horaInicioParameterDetail, false);

                //listado de HhppRR cargados      
                if (consultaPaginacionDto != null && consultaPaginacionDto.getListResultado() != null
                        && !consultaPaginacionDto.getListResultado().isEmpty()) {
                    for (HhppRR hhppRR : consultaPaginacionDto.getListResultado()) {
                        HhppMglLogRr hhppMglLogRr = new HhppMglLogRr();
                        hhppMglLogRr.setFechaAudLogSiglo(hhppRR.getFechaAudSiglo());
                        hhppMglLogRr.setFechaAudLogAño(hhppRR.getFechaAudAño());
                        hhppMglLogRr.setFechaAudLogMes(hhppRR.getFechaAudMes());
                        hhppMglLogRr.setFechaAudLogDia(hhppRR.getFechaAudDia());
                        hhppMglLogRr.setFechaAudLogSiglo2(hhppRR.getFechaAudSiglo2());
                        hhppMglLogRr.setFechaAudLogAño2(hhppRR.getFechaAudAño2());
                        hhppMglLogRr.setFechaAudLogMes2(hhppRR.getFechaAudMes2());
                        hhppMglLogRr.setFechaAudLogDia2(hhppRR.getFechaAudDia2());

                        hhppMglLogRr.setComunidad(hhppRR.getComunidad());
                        hhppMglLogRr.setDivision(hhppRR.getDivision());
                        hhppMglLogRr.setVendedor(hhppRR.getVendedor());
                        hhppMglLogRr.setTipoAcometida(hhppRR.getTipoAcometida());
                        hhppMglLogRr.setTipoCable(hhppRR.getTipoCable());
                        hhppMglLogRr.setHeadEnd(hhppRR.getHeadEnd());
                        hhppMglLogRr.setTipoHhpp(hhppRR.getTipoHhpp());
                        hhppMglLogRr.setCodigoPostal(hhppRR.getCodigoPostal());
                        hhppMglLogRr.setNodo(hhppRR.getNodo());
                        hhppMglLogRr.setUltimaUbicacion(hhppRR.getUltimaUbicacion());
                        hhppMglLogRr.setEstrato(hhppRR.getEstrato());
                        hhppMglLogRr.setIdRR(hhppRR.getIdUnitMstr());
                        hhppMglLogRr = hhppMglLogRrManager.create(hhppMglLogRr);
                        if (hhppMglLogRr.getHhppLogRrId() != null) {
                            con++;
                            LOGGER.info("Registro creado en BD satisfatoriamente.");
                        }
                    }

                }
                consultaPaginacionDto= null;
                //Cada pagina se ejecuta el garbage collection 
                
                System.gc();   
            }
            if (con == totalPag) {
                LOGGER.info("Se copiaron todos los registros en MER");
                result = true;
            } else {
                LOGGER.error("No se copiaron todos los registros en BD");
                result = false;
            }
        } else {
            LOGGER.error("No hay registros a consultar");
            result = false;
        }

        return result;
    }
    

    private Parametros buscarParametroEnMglParametros(String nombreParametro) throws ApplicationException {
        ResourceEJB recursos = new ResourceEJB();
        return recursos.queryParametros(nombreParametro);
    }

    /**
     *
     * @return @throws Exception
     */
    private boolean uploadFile() throws ApplicationException {
        try {
            ResourceEJB recursos = new ResourceEJB();
            Parametros param = null;
            AddressRequest address = null;
            param = recursos.queryParametros(Constant.ROOT_UPLOAD);
            String rootUpload = param.getValor();
            param = recursos.queryParametros(Constant.UPLOAD_MASIVOS);
            String rutaCarga = param.getValor();
            ConsultaSolicitudEJB comunidad;
            manejoArchivos myManejoArchivos = new manejoArchivos();
            ArrayList<String> nombreArchivos = new ArrayList<>();
            comunidad = new ConsultaSolicitudEJB();
            ArrayList<String> ArchivoProcesando;
            ArrayList<String> ArchivoTerminado;
            String errorMessage;
            GeograficoPolitico lineaGeoPolitico;
            GeograficoPolitico lineaCiudad;
            AddressGeodata addressGeodata;
            Comunidad lineaComunidad;
            String lienaEstado;

            AddressEJB geoWs = new AddressEJB();

            ArrayList<ArrayList<String>> listaArchivos = myManejoArchivos.leerListaArchivos(rutaCarga, rootUpload, nombreArchivos, "Catastro", "csv");
            for (int contArchivos = 0; listaArchivos.size() > contArchivos; contArchivos++) {
                if (!myManejoArchivos.validarArchivo((ArrayList<String>) listaArchivos.get(contArchivos), ",", 7, null)) {
                    String NombreArchivo = nombreArchivos.get(contArchivos);
                    String nombreDivididoSinExtension = NombreArchivo.replace(".csv", "");
                    if (myManejoArchivos.crearArchivo(nombreDivididoSinExtension + "Error.csv", rutaCarga, rootUpload, (ArrayList<String>) listaArchivos.get(contArchivos), "Error en el formato del archivo")) {
                        myManejoArchivos.eleminarArchivo(NombreArchivo, rutaCarga, rootUpload);
                    }
                } else {
                    ArchivoProcesando = (ArrayList<String>) listaArchivos.get(contArchivos);
                    ArchivoTerminado = new ArrayList<>();
                    for (int contLineas = 0; ArchivoProcesando.size() > contLineas; contLineas++) {

                        lineaCiudad = null;

                        String linea = ArchivoProcesando.get(contLineas);
                        String[] lineaDividedaEnColumnas = linea.split(",");
                        address = new AddressRequest();
                        errorMessage = "";
                        String complemento = lineaDividedaEnColumnas[3];
                        lineaComunidad = comunidad.queryComunidad(lineaDividedaEnColumnas[4]);
                        if (lineaComunidad != null) {
                            lineaGeoPolitico = lineaComunidad.getGeograficoPolitico();
                            if (lineaGeoPolitico != null) {
                                lineaCiudad = comunidad.queryGeograficoPolitico(lineaGeoPolitico.getGpoId());
                                if (lineaCiudad != null) {
                                    address.setCity(lineaCiudad.getGpoNombre());
                                } else {
                                    address.setCity("");
                                }
                            } else {
                                address.setCity("");
                            }
                        } else {
                            address.setCity("");
                        }
                        if (complemento.matches("^[-+]?\\d+(\\.\\d+)?$")) {
                            complemento = "AP " + complemento;
                        }
                        address.setNeighborhood(lineaDividedaEnColumnas[0]);
                        address.setAddress(lineaDividedaEnColumnas[2] + " " + lineaDividedaEnColumnas[1] + " " + complemento);
                        address.setLevel("C");
                        address.setId("1");
                        if (address.getAddress() != null) {
                            if (address.getAddress().equals("")) {
                                errorMessage = errorMessage + "Se debe digitar una direccion-";
                            }
                        } else {
                            errorMessage = errorMessage + "Se debe digitar una direccion-";
                        }
                        if (address.getCity() != null) {
                            if (address.getCity().equals("")) {
                                errorMessage = errorMessage + "Se debe digitar una ciudad. ó la ciudad no existe-";
                            }
                        } else {
                            errorMessage = errorMessage + "Se debe digitar una ciudad. ó la ciudad no existe-";
                        }

                        if (lineaCiudad != null) {
                            if (lineaCiudad.getGpoMultiorigen().equals("1")) {
                                if (address.getNeighborhood() != null) {
                                    if (address.getNeighborhood().equals("")) {
                                        errorMessage = errorMessage + "Se debe digitar un barrio para ciudades multiorigen-";
                                    }
                                } else {
                                    errorMessage = errorMessage + "Se debe digitar un barrio para ciudades multiorigen-";
                                }
                            }
                        }

                        lienaEstado = lineaDividedaEnColumnas[6];
                        if (lienaEstado != null) {
                            if (lienaEstado.equals("")) {
                                errorMessage = errorMessage + "El campo estado es obligatorio-";
                            }
                        } else {
                            errorMessage = errorMessage + "El campo estado es obligatorio-";
                        }

                        if (errorMessage.equals("")) {
                            addressGeodata = geoWs.queryAddressGeodata(address);
                            if (addressGeodata != null) {
                                Hhpp myHhpp;
                                String codigoDireccion = addressGeodata.getCoddir();
                                String codigoDireccion2 = addressGeodata.getCodencont();
                                String codComplemento = codigoDireccion.substring(codigoDireccion2.length(), codigoDireccion.length());
                                if (codComplemento.matches("^[-+]?\\d+(\\.\\d+)?$")) {
                                    myHhpp = queryDireccionHhppcity(codigoDireccion2, address.getCity());
                                } else {
                                    myHhpp = querySubDireccionHhppcity(codigoDireccion, address.getCity());
                                }
                                if (myHhpp != null) {
                                    if (queryEstado(myHhpp.getEstadoHhpp().getEhhId(), lienaEstado)) {
                                        if (updateEstadoHhpp(lienaEstado, "CargaAutomatico", myHhpp, "Actualizacion Estados de RR").equals(MESSAGE_OK)) {
                                            errorMessage = errorMessage + "ok";
                                        } else {
                                            errorMessage = errorMessage + "Error de DB al intentar actualizar-";
                                        }
                                    } else {
                                        errorMessage = errorMessage + "No se puede pasar del estado" + myHhpp.getEstadoHhpp().getEhhId() + " al estado " + lienaEstado + "-";
                                    }
                                } else {
                                    errorMessage = errorMessage + "El HHPP no existe en el repositorio-";
                                }
                            } else {
                                errorMessage = errorMessage + "Direccio no se georreferencio-";
                            }

                        }

                        ArchivoTerminado.add(linea + "," + errorMessage);
                    }//end for recorre lineas de archivos  
                    String NombreArchivo = nombreArchivos.get(contArchivos);
                    String nombreDivididoSinExtension = NombreArchivo.replace(".csv", "");
                    if (myManejoArchivos.crearArchivo(nombreDivididoSinExtension + "Procesado.csv", rutaCarga, rootUpload, ArchivoTerminado, "")) {
                        myManejoArchivos.eleminarArchivo(NombreArchivo, rutaCarga, rootUpload);
                    }

                }

            }//end for recorre archivos
            return true;
        } catch (ApplicationException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            return false;

        }

    }

    /**
     *
     * @param codigo
     * @param ciudad
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public Hhpp queryDireccionHhppcity(String codigo, String ciudad) throws ApplicationException {
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            Hhpp hhpp = null;
            EstadoHhpp estadoHhpp;
            DataObject obj = adb.outDataObjec("estd1", codigo, ciudad);
            if (obj != null) {
                hhpp = new Hhpp();
                estadoHhpp = new EstadoHhpp();
                BigDecimal iddepto = obj.getBigDecimal("HHP_ID");
                String estado = obj.getString("EHH_ID");
                hhpp.setHhppId(iddepto);
                estadoHhpp.setEhhId(estado);
                hhpp.setEstadoHhpp(estadoHhpp);
            }
            DireccionUtil.closeConnection(adb);
            return hhpp;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar la dirección del HHPP por ciudad. EX000: " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param codigo
     * @param ciudad
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public Hhpp querySubDireccionHhppcity(String codigo, String ciudad) throws ApplicationException {
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            Hhpp hhpp = null;
            EstadoHhpp estadoHhpp;
            DataObject obj = adb.outDataObjec("estsb2", codigo, ciudad);
            if (obj != null) {
                hhpp = new Hhpp();
                estadoHhpp = new EstadoHhpp();
                BigDecimal iddepto = obj.getBigDecimal("HHP_ID");
                String estado = obj.getString("EHH_ID");
                hhpp.setHhppId(iddepto);
                estadoHhpp.setEhhId(estado);
                hhpp.setEstadoHhpp(estadoHhpp);
            }
            DireccionUtil.closeConnection(adb);
            return hhpp;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar la subdirección del HHPP por ciudad. EX000: " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param estadoAnterior
     * @param estadoNuevo
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public boolean queryEstado(String estadoAnterior, String estadoNuevo) throws ApplicationException {
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("test1", estadoAnterior, estadoNuevo);
            if (obj != null) {
                DireccionUtil.closeConnection(adb);
                return true;
            } else {
                DireccionUtil.closeConnection(adb);
                return false;
            }
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar el estado del HHPP. EX000: " + e.getMessage(), e);
        }
    }

    @Override
    public EstadoHhpp queryEstadoHhpp(String nombreEstado) throws ApplicationException {
        return manager.queryEstadoHhpp(nombreEstado);
    }
}
