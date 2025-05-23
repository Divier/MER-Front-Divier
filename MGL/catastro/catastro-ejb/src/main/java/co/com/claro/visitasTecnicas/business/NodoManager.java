package co.com.claro.visitasTecnicas.business;

import co.com.claro.mgl.businessmanager.address.NodoMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtBasicaMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.telmex.catastro.data.Area;
import co.com.telmex.catastro.data.Comunidad;
import co.com.telmex.catastro.data.Distrito;
import co.com.telmex.catastro.data.Divisional;
import co.com.telmex.catastro.data.Geografico;
import co.com.telmex.catastro.data.GeograficoPolitico;
import co.com.telmex.catastro.data.Nodo;
import co.com.telmex.catastro.data.TipoGeografico;
import co.com.telmex.catastro.data.UnidadGestion;
import co.com.telmex.catastro.data.Zona;
import co.com.telmex.catastro.services.solicitud.ConsultaSolicitudEJB;
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
import java.util.Date;
import javax.persistence.NoResultException;

/**
 *
 * @author user
 */
public class NodoManager {

    private static final Logger LOGGER = LogManager.getLogger(NodoManager.class);

    /**
     *
     * @param nombreNodo
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public Nodo queryNodo(String nombreNodo) throws ApplicationException {
        Nodo nodo = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("nodV", nombreNodo);
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                nodo = new Nodo();
                nodo.setNodId(obj.getBigDecimal("NODO_ID"));
                BigDecimal gpo_idN = obj.getBigDecimal("GEOGRAFICO_POLITICO_ID");
                if (gpo_idN != null) {
                    GeograficoPolitico geograficoPo = new GeograficoPolitico();
                    geograficoPo = queryGeograficoPolitico(gpo_idN);
                    nodo.setGeograficoPolitico(geograficoPo);
                }
                nodo.setNodNombre(obj.getString("NOMBRE"));
                nodo.setNodCodigo(obj.getString("CODIGO"));
                BigDecimal uge_id = obj.getBigDecimal("UNIDAD_GESTION_BASICA_ID");
                if (uge_id != null) {
                    UnidadGestion ugestion = new UnidadGestion();
                    ugestion = queryUnidadGestion(uge_id);
                    nodo.setUnidadGestion(ugestion);
                }
                BigDecimal zon_id = obj.getBigDecimal("ZONA_BASICA_ID");
                if (zon_id != null) {
                    Zona zona = new Zona();
                    zona = queryZona(zon_id);
                    nodo.setZona(zona);
                }
                BigDecimal dis_id = obj.getBigDecimal("DISTRITO_BASICA_ID");
                if (dis_id != null) {
                    Distrito distrito = new Distrito();
                    distrito = queryDistrito(dis_id);
                    nodo.setDistrito(distrito);
                }
                BigDecimal div_id = obj.getBigDecimal("DIVISIONAL_BASICA_ID");
                if (div_id != null) {
                    Divisional divisional = new Divisional();
                    divisional = queryDivisional(div_id);
                    nodo.setDivisional(divisional);
                }

                BigDecimal are_id = obj.getBigDecimal("AREA_BASICA_ID");
                if (are_id != null) {
                    Area area = new Area();
                    area = queryArea(are_id);
                    nodo.setArea(area);
                }
                String nod_headEnd = obj.getString("HEAD_END");
                nodo.setNodHeadend(nod_headEnd);
                BigDecimal nod_Tipo = obj.getBigDecimal("TIPO");
                nodo.setNodTipo(nod_Tipo);

            }
            return nodo;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar el nodo. EX000: " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param codigoNodo
     * @param gpo_id
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public Nodo queryNodo(String codigoNodo, BigDecimal gpo_id) throws ApplicationException {
        Nodo nodo = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("nod1", codigoNodo, gpo_id);
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                ConsultaSolicitudEJB consultaEJB = new ConsultaSolicitudEJB();
                nodo = new Nodo();
                nodo.setNodId(obj.getBigDecimal("NODO_ID"));
                BigDecimal gpo_idN = obj.getBigDecimal("GEOGRAFICO_POLITICO_ID");
                if (gpo_idN != null) {
                    GeograficoPolitico geograficoPo = new GeograficoPolitico();
                    geograficoPo = queryGeograficoPolitico(gpo_idN);
                    nodo.setGeograficoPolitico(geograficoPo);
                }
                nodo.setNodNombre(obj.getString("NOMBRE"));
                nodo.setNodCodigo(obj.getString("CODIGO"));
                BigDecimal uge_id = obj.getBigDecimal("UNIDAD_GESTION_BASICA_ID");
                if (uge_id != null) {
                    UnidadGestion ugestion = new UnidadGestion();
                    ugestion = queryUnidadGestion(uge_id);
                    nodo.setUnidadGestion(ugestion);
                }
                BigDecimal zon_id = obj.getBigDecimal("ZONA_BASICA_ID");
                if (zon_id != null) {
                    Zona zona = new Zona();
                    zona = queryZona(zon_id);
                    nodo.setZona(zona);
                }
                BigDecimal dis_id = obj.getBigDecimal("DISTRITO_BASICA_ID");
                if (dis_id != null) {
                    Distrito distrito = new Distrito();
                    distrito = queryDistrito(dis_id);
                    nodo.setDistrito(distrito);
                }
                BigDecimal div_id = obj.getBigDecimal("DIVISIONAL_BASICA_ID");
                if (div_id != null) {
                    Divisional divisional = new Divisional();
                    divisional = queryDivisional(div_id);
                    nodo.setDivisional(divisional);
                }

                BigDecimal are_id = obj.getBigDecimal("AREA_BASICA_ID");
                if (are_id != null) {
                    Area area = new Area();
                    area = queryArea(are_id);
                    nodo.setArea(area);
                }
                String nod_headEnd = obj.getString("HEAD_END");
                nodo.setNodHeadend(nod_headEnd);
                BigDecimal nod_Tipo = obj.getBigDecimal("TIPO");
                nodo.setNodTipo(nod_Tipo);
                BigDecimal id_com = obj.getBigDecimal("COM_ID");
                if (id_com != null) {
                    Comunidad comunidad = new Comunidad();

                      comunidad.setComId(id_com.toString());
                      nodo.setComunidad(comunidad);
                }



            }
            return nodo;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+
                    ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar el nodo. EX000: " + e.getMessage(), e);
        }
    }

    /**
     * Carga un Geografico (Localidad, Barrio o Manzana) que con id geo_id
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
                geografico.setGeoId(obj.getBigDecimal("GEOGRAFICO_ID"));
                geografico.setGeoNombre(obj.getString("NOMBRE"));
                BigDecimal gpo_id = obj.getBigDecimal("GEOGRAFICO_POLITICO_ID");
                if (gpo_id != null) {
                    geografico.setGeograficoPolitico(queryGeograficoPolitico(gpo_id));
                }
                TipoGeografico tipoG = new TipoGeografico();
                BigDecimal tge_id = obj.getBigDecimal("TIPO_GEOGRAFICO_ID");
                tipoG = queryTipoGeografico(tge_id);
                geografico.setTipoGeografico(tipoG);
                BigDecimal geo_geo_id = obj.getBigDecimal("GEOGRAFICO_GEOGRAFICO_ID");
                Geografico geo_geo = new Geografico();
                if (geo_geo_id != null) {
                    geo_geo = queryGeografico(geo_geo_id);
                }
                geografico.setGeografico(geo_geo);
            }
            return geografico;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar el geográfico. EX000: " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param idNodo
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public Nodo queryNodo(BigDecimal idNodo) throws ApplicationException {
        Nodo nodo = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("nod2", idNodo.toString());
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                nodo = new Nodo();
                nodo.setNodId(obj.getBigDecimal("NODO_ID"));
                GeograficoPolitico geograficoPo = new GeograficoPolitico();
                BigDecimal gpo_id = obj.getBigDecimal("GEOGRAFICO_POLITICO_ID");
                geograficoPo = queryGeograficoPolitico(gpo_id);
                nodo.setGeograficoPolitico(geograficoPo);
                nodo.setNodNombre(obj.getString("NOMBRE"));
                nodo.setNodCodigo(obj.getString("CODIGO"));
                UnidadGestion ugestion = new UnidadGestion();
                BigDecimal uge_id = obj.getBigDecimal("UNIDAD_GESTION_BASICA_ID");
                ugestion = queryUnidadGestion(uge_id);
                nodo.setUnidadGestion(ugestion);
                Zona zona = new Zona();
                BigDecimal zon_id = obj.getBigDecimal("ZONA_BASICA_ID");
                zona = queryZona(zon_id);
                nodo.setZona(zona);
                Distrito distrito = new Distrito();
                BigDecimal dis_id = obj.getBigDecimal("DISTRITO_BASICA_ID");
                distrito = queryDistrito(dis_id);
                nodo.setDistrito(distrito);
                Divisional divisional = new Divisional();
                BigDecimal div_id = obj.getBigDecimal("DIVISIONAL_BASICA_ID");
                divisional = queryDivisional(div_id);
                nodo.setDivisional(divisional);
                Area area = new Area();
                BigDecimal are_id = obj.getBigDecimal("AREA_BASICA_ID");
                area = queryArea(are_id);
                nodo.setArea(area);
                String head_end = obj.getString("HEAD_END");
                nodo.setNodHeadend(head_end);
            }
            return nodo;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);            
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar el nodo. EX000: " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param uge_id
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public UnidadGestion queryUnidadGestion(BigDecimal uge_id) throws ApplicationException {
        UnidadGestion UnidadGe = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("uge1", uge_id.toString());
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                UnidadGe = new UnidadGestion();
                UnidadGe.setUgeId(obj.getBigDecimal("BASICA_ID")); //UGE_ID
                UnidadGe.setUgeNombre(obj.getString("NOMBRE_BASICA"));//UGE_NOMBRE
            }
            return UnidadGe;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);            
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar la unidad de gestión. EX000: " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param are_id
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public Area queryArea(BigDecimal are_id) throws ApplicationException {
        Area area = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("are1", are_id.toString());
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                area = new Area();
                area.setAreId(obj.getBigDecimal("BASICA_ID"));//ARE_ID
                area.setAreNombre(obj.getString("NOMBRE_BASICA"));//ARE_NOMBRE
            }
            return area;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);            
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar el área. EX000: " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param zon_id
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public Zona queryZona(BigDecimal zon_id) throws ApplicationException {
        Zona zona = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("zon1", zon_id.toString());
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                zona = new Zona();
                zona.setZonId(obj.getBigDecimal("BASICA_ID"));//ZON_ID
                zona.setZonNombre(obj.getString("NOMBRE_BASICA"));//ZON_NOMBRE
            }
            return zona;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar la zona. EX000: " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param dis_id
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public Distrito queryDistrito(BigDecimal dis_id) throws ApplicationException {
        Distrito distrito = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("dis1", dis_id);
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                distrito = new Distrito();
                BigDecimal dstr_id = obj.getBigDecimal("BASICA_ID");
                String id_dist = dstr_id.toString();
                distrito.setDisId(id_dist);//DIS_ID
                distrito.setDisNombre(obj.getString("NOMBRE_BASICA"));//DIS_NOMBRE
            }
            return distrito;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar el distrito. EX000: " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param div_id
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public Divisional queryDivisional(BigDecimal div_id) throws ApplicationException {
        Divisional divisional = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("div1", div_id.toString());
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                divisional = new Divisional();
                divisional.setDivId(obj.getBigDecimal("BASICA_ID"));//DIV_ID
                divisional.setDivNombre(obj.getString("NOMBRE_BASICA"));//DIV_NOMBRE
            }
            return divisional;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);            
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar la divisional. EX000: " + e.getMessage(), e);
        }
    }

    /**
     * Carga un Geografico Politico : País, Departamento o Ciudad.
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
                geograficoPo.setGpoNombre(obj.getString("NOMBRE"));
                geograficoPo.setGpoCodigoDane(obj.getString("CODIGO"));
                geograficoPo.setGpoTipo(obj.getString("TIPO"));
                geograficoPo.setGpoMultiorigen(obj.getString("MULTIORIGEN"));
                geograficoPo.setGpoCodTipoDireccion(obj.getString("COD_TIPO_DIRECCION"));
                geograficoPo.setGpoMultiorigen(obj.getString("CODIGODANE"));
                geograficoPo.setGpoCodigo(obj.getString("CODIGO"));
                BigDecimal geo_gpo_id = obj.getBigDecimal("GEOGRAFICO_GEO_POLITICO_ID");
                if (geo_gpo_id != null) {
                    GeograficoPolitico geo_gpo = queryGeograficoPolitico(geo_gpo_id);
                    geograficoPo.setGeograficoPolitico(geo_gpo);
                }
            }
            return geograficoPo;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);            
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar el geográfico político. EX000: " + e.getMessage(), e);
        }
    }

    /**
     * Carga el Tipo de Geografico (Ciudad, Pais, Departamento,
     * Localidad,Barrio, Manzana)
     *
     * @param tge_id
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public TipoGeografico queryTipoGeografico(BigDecimal tge_id) throws ApplicationException {
        TipoGeografico tipoG = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("tge1", tge_id.toString());
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                tipoG = new TipoGeografico();
                tipoG.setTgeId(obj.getBigDecimal("TGE_ID"));
                tipoG.setTgeValor(obj.getString("TGE_VALOR"));
            }
            return tipoG;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar el tipo geográfico. EX000: " + e.getMessage(), e);
        }
    }

    public boolean createNodo(Nodo nodo) throws ApplicationException {
        boolean insertado = false;

        try {
            Date now = new Date();
            NodoMgl nodoMgl = new NodoMgl();
            nodoMgl.setGpoId(nodo.getGeograficoPolitico().getGpoId());
            nodoMgl.setNodNombre(nodo.getNodNombre());
            nodoMgl.setNodCodigo(nodo.getNodCodigo());
            nodoMgl.setNodFechaActivacion(now);
            nodoMgl.setNodFechaCreacion(now);
            nodoMgl.setNodUsuarioCreacion(nodo.getNodUsuarioCreacion());
            nodoMgl.setNodFechaModificacion(now);
            nodoMgl.setNodUsuarioModificacion(nodo.getNodUsuarioModificacion());
            
            //valbuenayf inicio ajuste para el tipo de tecnologia del nodo
            CmtBasicaMgl nodTipo;
            CmtBasicaMglManager cmtBasicaMglManager = new CmtBasicaMglManager();
            //TODO:revisar
            if (nodo.getNodTipo() != null 
                    && !nodo.getNodTipo().toString().trim().isEmpty()
                    && nodo.getNodTipo().toString().trim().matches("^[0-9]")) {
                try {
                    nodTipo = cmtBasicaMglManager.findById(nodo.getNodTipo());
                } catch (NoResultException e) {
                    String msg = "Se produjo un error al momento de ejecutar el método '"+
                        ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                    LOGGER.error(msg);
                    nodTipo = null;
                }

            } else {
                nodTipo = null;
            }
            //valbuenayf fin ajuste para el tipo de tecnologia del nodo
            nodoMgl.setNodTipo(nodTipo);
            
            NodoMglManager nodoMglManager = new NodoMglManager();
            nodoMgl = nodoMglManager.create(nodoMgl);
            if ( nodoMgl != null 
                    && nodoMgl.getNodId() != null 
                    && nodoMgl.getNodId().compareTo(BigDecimal.ZERO)!= 0){
                insertado = true;
            }
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de crear el nodo. EX000: " + e.getMessage(), e);
        } 
        return insertado;
    }

    /**
     * Consultar una comunidad
     *
     * @param idComunidad Identificador de la comunidad a buscar.
     * @return comunidad con id ingresado
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public Comunidad queryComunidad(String idComunidad) throws ApplicationException {
        Comunidad comunidad = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("com1", idComunidad);
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                comunidad = new Comunidad();
                comunidad.setComId(obj.getString("COM_ID"));
                GeograficoPolitico gpo = new GeograficoPolitico();
                gpo.setGpoId(obj.getBigDecimal("GEOGRAFICO_POLITICO_ID"));
                comunidad.setGeograficoPolitico(gpo);
                comunidad.setComNombre(obj.getString("COM_NOMBRE"));
                comunidad.setComZipcode(obj.getString("COM_ZIPCODE"));
            }
            return comunidad;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar la comunidad. EX000: " + e.getMessage(), e);
        }
    }

    public NodoRR queryNodoRR(String strNodo) throws ApplicationException {
        NodoRR nodoRR = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("rrnode", strNodo);
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                nodoRR = new NodoRR();
                nodoRR.setCodCiudad(obj.getString("CODCIUDAD"));
                nodoRR.setCodArea(obj.getString("CODAREA"));
                nodoRR.setCodDistrito(obj.getString("CODDISTRITO"));
                nodoRR.setCodEq(obj.getString("CODEQ"));
                nodoRR.setCodRegional(obj.getString("CODREGIONAL"));
                nodoRR.setCodUnidad(obj.getString("CODUNIDAD"));
                nodoRR.setCodZona(obj.getString("CODZONA"));
                nodoRR.setCodigo(obj.getString("CODIGO"));
                nodoRR.setDicDivision(obj.getString("CODDIVISION"));
                nodoRR.setEstado(obj.getString("ESTADO"));
                nodoRR.setNombre(obj.getString("NOMBRE"));
                nodoRR.setReferencia(obj.getString("REFERENCIA"));
            }

            return nodoRR;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);            
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar el nodo de RR. EX000: " + e.getMessage(), e);
        }
    }

    public NodoRR queryNodoRR(String strNodo, String ciudad) throws ApplicationException {
        NodoRR nodoRR = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("rrnode2",strNodo);
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                nodoRR = new NodoRR();
                nodoRR.setCodCiudad(obj.getString("CODCIUDAD"));
                nodoRR.setCodArea(obj.getString("CODAREA"));
                nodoRR.setCodDistrito(obj.getString("CODDISTRITO"));
                nodoRR.setCodEq(obj.getString("CODEQ"));
                nodoRR.setCodRegional(obj.getString("CODREGIONAL"));
                nodoRR.setCodUnidad(obj.getString("CODUNIDAD"));
                nodoRR.setCodZona(obj.getString("CODZONA"));
                nodoRR.setCodigo(obj.getString("CODIGO"));
                nodoRR.setDicDivision(obj.getString("CODDIVISION"));
                nodoRR.setEstado(obj.getString("ESTADO"));
                nodoRR.setNombre(obj.getString("NOMBRE"));
                nodoRR.setReferencia(obj.getString("REFERENCIA"));
            }

            return nodoRR;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);            
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar el nodo de RR. EX000: " + e.getMessage(), e);
        }
    }

    public ArrayList<NodoRR> queryGetNodoRRNfiByCodCiudad(String codCiudad) throws ApplicationException {
        ArrayList<NodoRR> nodoRRList = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList objList = adb.outDataList("rrnodeNFICodCiudad", codCiudad.toUpperCase());
            DireccionUtil.closeConnection(adb);
            if (objList != null) {
                nodoRRList = new ArrayList<NodoRR>();
                for (DataObject obj : objList.getList()) {
                    NodoRR nodoRR = new NodoRR();
                    nodoRR.setCodCiudad(obj.getString("CODCIUDAD"));
                    nodoRR.setCodArea(obj.getString("CODAREA"));
                    nodoRR.setCodDistrito(obj.getString("CODDISTRITO"));
                    nodoRR.setCodEq(obj.getString("CODEQ"));
                    nodoRR.setCodRegional(obj.getString("CODREGIONAL"));
                    nodoRR.setCodUnidad(obj.getString("CODUNIDAD"));
                    nodoRR.setCodZona(obj.getString("CODZONA"));
                    nodoRR.setCodigo(obj.getString("CODIGO"));
                    nodoRR.setDicDivision(obj.getString("CODDIVISION"));
                    nodoRR.setEstado(obj.getString("ESTADO"));
                    nodoRR.setNombre(obj.getString("NOMBRE"));
                    nodoRR.setReferencia(obj.getString("REFERENCIA"));
                    if (nodoRR.getCodigo() != null && !nodoRR.getCodigo().isEmpty()) {
                        nodoRRList.add(nodoRR);
                    }
                }
            }
            return nodoRRList;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);            
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar el nodo RR por código ciudad. EX000: " + e.getMessage(), e);
        }
    }
}
