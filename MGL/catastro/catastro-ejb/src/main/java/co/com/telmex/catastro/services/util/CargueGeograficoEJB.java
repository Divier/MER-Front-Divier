package co.com.telmex.catastro.services.util;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.telmex.catastro.data.CargueGeografico;
import co.com.telmex.catastro.data.Geografico;
import co.com.telmex.catastro.data.GeograficoPolitico;
import co.com.telmex.catastro.data.Multivalor;
import co.com.telmex.catastro.data.TipoGeografico;
import co.com.telmex.catastro.data.TipoHhpp;
import co.com.telmex.catastro.data.TipoHhppConexion;
import co.com.telmex.catastro.data.TipoHhppRed;
import co.com.telmex.catastro.data.TipoMarcas;
import co.com.telmex.catastro.services.solicitud.SolicitudRedEJB;
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
 * Clase CargueGeograficoEJB Implementa CargueGeograficoEJBRemote
 *
 * @author Ana María Malambo
 * @version	1.0
 */
@Stateless(name = "cargueGeograficoEJB", mappedName = "cargueGeograficoEJB", description = "cargueGeografico")
@Remote({CargueGeograficoEJBRemote.class})
public class CargueGeograficoEJB implements CargueGeograficoEJBRemote {

    private static final Logger LOGGER = LogManager.getLogger(CargueGeograficoEJB.class);

    /**
     *
     * @param solicitud
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public BigDecimal insertSolicitudNegocio(CargueGeografico solicitud) throws ApplicationException {
        BigDecimal idson = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            adb.in("son1", solicitud.getGeograficoPolitico().getGpoId().toString(), solicitud.getNodo().getNodId().toString(),
                    solicitud.getTipoHhpp().getThhId(), solicitud.getGeografico().getGeoId().toString(),
                    solicitud.getTipoHhppRed().getThrId().toString(), solicitud.getTipoHhppConexion().getThcId().toString(),
                    solicitud.getTipoMarcas().getTmaId().toString(), solicitud.getSonCuentaMatriz(), solicitud.getSonMotivo(),
                    solicitud.getSonRespuesta(), solicitud.getSonNomSolicitante(), solicitud.getSonContacto(), solicitud.getSonTelContacto(),
                    solicitud.getSonEstado(), solicitud.getSonTipoSolucion(),
                    solicitud.getSonResGestion(), solicitud.getSonCorregirHhpp(), solicitud.getSonCambioNodo(), solicitud.getSonNuevoProducto(),
                    solicitud.getSonEstratoAntiguo(), solicitud.getSonEstratoNuevo(), solicitud.getSonUsuarioCreacion(), solicitud.getSonObservaciones(),
                    solicitud.getSonFormatoIgac(), solicitud.getSonServinformacion(), solicitud.getSonNostandar(), solicitud.getSonTipoSolicitud(), solicitud.getSonEstrato(),
                    solicitud.getSonZipcode(), solicitud.getSonDiralterna(), solicitud.getSonLocalidad(), solicitud.getSonMz(), solicitud.getSonLongitud(),
                    solicitud.getSonLatitud(), solicitud.getSonLadomz(), solicitud.getSonHeadEnd(), solicitud.getSonCodSolicitante(), solicitud.getSonEmailSolicitante(),
                    solicitud.getSonCelSolicitante(), solicitud.getSonEstadoSol(), solicitud.getSonEstadoUni(), solicitud.getSonUsuGestion(),
                    solicitud.getSonCampoa1(), solicitud.getSonCampoa2(), solicitud.getSonCampoa3(), solicitud.getSonCampoa4(), solicitud.getSonCampoa5());
            idson = getIdSolicitudNegocio(adb);
            DireccionUtil.closeConnection(adb);
            return idson;
        } catch (Exception e) {
             LOGGER.error("Se produjo un error al momento de ejecutar el método .'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ e.getMessage());
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de insertar la solicitud. EX000: " + e.getMessage(), e);
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
             LOGGER.error("Se produjo un error al momento de ejecutar el método .'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ ex.getMessage());
            throw new ApplicationException("Error al momento de obtener el ID de la solicitud. EX000: " + ex.getMessage(), ex);
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
            LOGGER.error("Se produjo un error al momento de ejecutar el método .'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ e.getMessage());
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de cargar los tipos de solicitud. EX000: " + e.getMessage(), e);
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
             LOGGER.error("Se produjo un error al momento de ejecutar el método .'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ e.getMessage());
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar los paises. EX000: " + e.getMessage(), e);
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
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("gpo4", idPais.toString());
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                regionales = new ArrayList<GeograficoPolitico>();
                for (DataObject obj : list.getList()) {
                    BigDecimal gpo_id = obj.getBigDecimal("GEOGRAFICO_POLITICO_ID");
                    BigDecimal geo_gpo_id = obj.getBigDecimal("GEO_GPO_ID");
                    String name = obj.getString("GPO_NOMBRE");
                    GeograficoPolitico region = new GeograficoPolitico();
                    region.setGpoId(gpo_id);
                    GeograficoPolitico pais = new GeograficoPolitico();
                    pais = queryGeograficoPolitico(geo_gpo_id);
                    region.setGeograficoPolitico(pais);
                    region.setGpoNombre(name);
                    regionales.add(region);
                }
            }
            return regionales;
        } catch (Exception e) {
            LOGGER.error("Se produjo un error al momento de ejecutar el método .'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ e.getMessage());
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar las regionales. EX000: " + e.getMessage(), e);
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
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("gpo3", idRegional.toString());
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                ciudades = new ArrayList<GeograficoPolitico>();
                for (DataObject obj : list.getList()) {
                    BigDecimal gpo_id = obj.getBigDecimal("GEOGRAFICO_POLITICO_ID");
                    BigDecimal geo_gpo_id = obj.getBigDecimal("GEO_GPO_ID");
                    String name = obj.getString("GPO_NOMBRE");
                    GeograficoPolitico ciudad = new GeograficoPolitico();
                    ciudad.setGpoId(gpo_id);
                    GeograficoPolitico pais = new GeograficoPolitico();
                    pais = queryGeograficoPolitico(geo_gpo_id);
                    ciudad.setGeograficoPolitico(pais);
                    ciudad.setGpoNombre(name);
                    ciudades.add(ciudad);
                }
            }
            return ciudades;
        } catch (Exception e) {
            LOGGER.error("Se produjo un error al momento de ejecutar el método .'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ e.getMessage());
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar las ciudades. EX000: " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param gpo_id
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public GeograficoPolitico queryGeograficoPolitico(BigDecimal gpo_id) throws ApplicationException {
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
                    GeograficoPolitico geo_gpo = queryGeograficoPolitico(geo_gpo_id);
                    geograficoPo.setGeograficoPolitico(geo_gpo);
                }
            }
            return geograficoPo;
        } catch (Exception e) {
            LOGGER.error("Se produjo un error al momento de ejecutar el método .'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ e.getMessage());
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultrar el geográfico político. EX000: " + e.getMessage(), e);
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
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("geo2", "%" + neighborhoodName + "%", gpo_id.toString());
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                geografico = new Geografico();
                geografico.setGeoId(obj.getBigDecimal("GEO_ID"));
                geografico.setGeoNombre(neighborhoodName);
                GeograficoPolitico gpo = queryGeograficoPolitico(gpo_id);
                geografico.setGeograficoPolitico(gpo);
                BigDecimal tge_id = obj.getBigDecimal("TGE_ID");
                SolicitudRedEJB srEJB = new SolicitudRedEJB();
                TipoGeografico tgeo = new TipoGeografico();
                geografico.setTipoGeografico(tgeo);
                BigDecimal geo_geo_id = obj.getBigDecimal("GEO_GEO_ID");
                if (geo_geo_id != null) {
                    Geografico geo_geo = srEJB.queryGeografico(geo_geo_id);
                    geografico.setGeografico(geo_geo);
                }
            }
            return geografico;
        } catch (Exception e) {
            LOGGER.error("Se produjo un error al momento de ejecutar el método .'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ e.getMessage());
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar el barrio. EX000: " + e.getMessage(), e);
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
            LOGGER.error("Se produjo un error al momento de ejecutar el método .'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ e.getMessage());
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar las calles. EX000: " + e.getMessage(), e);
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
            LOGGER.error("Se produjo un error al momento de ejecutar el método .'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ e.getMessage());
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar los cardinales. EX000: " + e.getMessage(), e);
        }
    }

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    @Override
    public List<Multivalor> queryLetras() throws ApplicationException {
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
            LOGGER.error("Se produjo un error al momento de ejecutar el método .'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ e.getMessage());
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar las letras. EX000: " + e.getMessage(), e);
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
            LOGGER.error("Se produjo un error al momento de ejecutar el método .'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ e.getMessage());
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar los prefijos. EX000: " + e.getMessage(), e);
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
            LOGGER.error("Se produjo un error al momento de ejecutar el método .'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ e.getMessage());
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar los tipos de HHPP. EX000: " + e.getMessage(), e);
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
            LOGGER.error("Se produjo un error al momento de ejecutar el método .'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ e.getMessage());
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar los tipos de conexión. EX000: " + e.getMessage(), e);
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
            LOGGER.error("Se produjo un error al momento de ejecutar el método .'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ e.getMessage());
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar los tipos de red. EX000: " + e.getMessage(), e);
        }
    }

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    @Override
    public List<TipoMarcas> queryBlackList() throws ApplicationException {
        List<TipoMarcas> tiposBlackL = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("tma1");
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                tiposBlackL = new ArrayList<TipoMarcas>();
                for (DataObject obj : list.getList()) {
                    BigDecimal tbl_id = obj.getBigDecimal("TMA_ID");
                    String codigo = obj.getString("TMA_CODIGO");
                    String nombre = obj.getString("TMA_NOMBRE");
                    TipoMarcas tbl = new TipoMarcas();
                    tbl.setTmaId(tbl_id);
                    tbl.setTmaCodigo(codigo);
                    tbl.setTmaNombre(nombre);
                    tiposBlackL.add(tbl);
                }
            }
            return tiposBlackL;
        } catch (ExceptionDB e) {
            LOGGER.error("Se produjo un error al momento de ejecutar el método .'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ e.getMessage());
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar los tipos de marcas blacklist. EX000: " + e.getMessage(), e);
        }
    }
}
