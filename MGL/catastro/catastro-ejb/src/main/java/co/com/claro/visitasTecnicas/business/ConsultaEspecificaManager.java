package co.com.claro.visitasTecnicas.business;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.telmex.catastro.data.Direccion;
import co.com.telmex.catastro.data.Geografico;
import co.com.telmex.catastro.data.GeograficoPolitico;
import co.com.telmex.catastro.data.SubDireccion;
import co.com.telmex.catastro.data.TipoDireccion;
import co.com.telmex.catastro.data.Ubicacion;
import co.com.telmex.catastro.services.comun.Constantes;
import co.com.telmex.catastro.services.util.DireccionUtil;
import com.jlcg.db.AccessData;
import com.jlcg.db.data.DataObject;
import com.jlcg.db.exept.ExceptionDB;
import com.jlcg.db.sql.ManageAccess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author user
 */
public class ConsultaEspecificaManager {

    private static final Logger LOGGER = LogManager.getLogger(ConsultaEspecificaManager.class);

    public Direccion queryAddressOnRepoById(String idDireccion) throws ApplicationException {
        Direccion direccion = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("dir6", idDireccion);
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                direccion = new Direccion();
                TipoDireccion tipo_dir = new TipoDireccion();
                Ubicacion ubi = new Ubicacion();
                BigDecimal id = obj.getBigDecimal("DIRECCION_ID");
                BigDecimal tdi_id = obj.getBigDecimal("TIPO_DIRECCION_ID");
                if (tdi_id != null) {
                    tipo_dir = queryTipoDireccionById(tdi_id.toString());
                }
                BigDecimal ubi_id = obj.getBigDecimal("UBICACION_ID");
                if (ubi_id != null) {
                    ubi = queryUbicacionById(ubi_id);
                }
                direccion.setDirFormatoIgac(obj.getString("FORMATO_IGAC"));
                direccion.setDirServinformacion(obj.getString("SERV_INFORMACION"));
                direccion.setDirNostandar(obj.getString("NO_STANDAR"));
                direccion.setDirOrigen(obj.getString("ORIGEN"));
                BigDecimal confiabilidad = obj.getBigDecimal("CONFIABILIDAD");
                if (confiabilidad != null) {
                    direccion.setDirConfiabilidad(confiabilidad);
                }
                BigDecimal estrato = obj.getBigDecimal("ESTRATO");
                if (estrato != null) {
                    direccion.setDirEstrato(estrato);
                }
                BigDecimal nvl_socio = obj.getBigDecimal("NIVEL_SOCIOECONOMICO");
                if (nvl_socio != null) {
                    direccion.setDirNivelSocioecono(nvl_socio);
                }
                direccion.setDirId(id);
                tipo_dir.setTdiId(tdi_id);
                direccion.setTipoDireccion(tipo_dir);
                ubi.setUbiId(ubi_id);
                direccion.setUbicacion(ubi);

                direccion.setDirActividadEconomica("COMENTARIO_SOCIOECONOMICO");//cambio carlos villamil Fase I catastro 2012-12-14
                direccion.setDirManzanaCatastral(obj.getString("MANZANA_CATASTRAL"));
                direccion.setDirManzana(obj.getString("MANZANA"));
                String fecha_creacion = obj.getString("FECHA_CREACION");
                if (fecha_creacion != null) {
                    direccion.setDirFechaCreacion(new Date(fecha_creacion));
                }
                direccion.setDirUsuarioCreacion(obj.getString("USUARIO_CREACION"));
                String fecha_mod = obj.getString("FECHA_EDICION");
                if (fecha_mod != null) {
                    direccion.setDirFechaModificacion(new Date(fecha_mod));
                }
                direccion.setDirUsuarioModificacion(obj.getString("USUARIO_EDICION"));
                direccion.setDirRevisar(obj.getString("REVISAR_DIRECCION"));
                direccion.setDirActividadEconomica(obj.getString("ACTIVIDAD_ECONOMICA"));
                direccion.setNodoUno(obj.getString(Constantes.DIR_NODOUNO));
                direccion.setNodoDos(obj.getString(Constantes.DIR_NODODOS));
                direccion.setNodoTres(obj.getString(Constantes.DIR_NODOTRES));

            }
            return direccion;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error en queryAddressOnRepoById. EX000 " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param idSubDireccion
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public SubDireccion querySubAddressOnRepositoryById(BigDecimal idSubDireccion) throws ApplicationException {
        SubDireccion subDir = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("sdi6", idSubDireccion.toString());
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                BigDecimal subDir_id = obj.getBigDecimal("SUB_DIRECCION_ID");
                BigDecimal dir_id = obj.getBigDecimal("DIRECCION_ID");
                String subDir_igac = obj.getString("FORMATO_IGAC");
                String subDir_ComentarioNivelSocioEconomico = obj.getString("COMENTARIO_SOCIOECONOMICO");//Modificacion Carlos Villamil direcciones fase I 2012-12-18
                String subDir_servi = obj.getString("SERVINFORMACION");
                BigDecimal subDir_estrato = obj.getBigDecimal("ESTRATO");
                BigDecimal subDir_nvSocio = obj.getBigDecimal("NIVEL_SOCIOECONO");
                BigDecimal subDir_actiEcon = obj.getBigDecimal("ACTIVIDAD_ECONO");
                String subDir_fechaCrea = obj.getString("FECHA_CREACION");
                String subDir_usuCrea = obj.getString("USUARIO_CREACION");
                String subDir_fechaModifica = obj.getString("FECHA_MODIFICACION");
                String subDir_usuModifica = obj.getString("USUARIO_MODIFICACION");

                subDir = new SubDireccion();
                subDir.setSdiId(subDir_id);
                Direccion direccion = new Direccion();
                direccion.setDirId(dir_id);
                subDir.setDireccion(direccion);
                subDir.setSdiFormatoIgac(subDir_igac);
                subDir.setSdiComentarioNivelSocioeconomico(subDir_ComentarioNivelSocioEconomico);//Modificacion Carlos Villamil direcciones fase I 2012-12-18
                subDir.setSdiEstrato(subDir_estrato);
                subDir.setSdiNivelSocioecono(subDir_nvSocio);
                subDir.setSdiActividadEcono(subDir_actiEcon);
                if (subDir_fechaCrea != null) {
                    subDir.setSdiFechaCreacion(new Date(subDir_fechaCrea));
                }
                subDir.setSdiUsuarioCreacion(subDir_usuCrea);
                if (subDir_fechaModifica != null) {
                    subDir.setSdiFechaModificacion(new Date(subDir_fechaModifica));
                }
                subDir.setSdiUsuarioModificacion(subDir_usuModifica);
                subDir.setSdiServinformacion(subDir_servi);
            }
            return subDir;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error en querySubAddressOnRepositoryById. EX000 " + e.getMessage(), e);
        }
    }

    private TipoDireccion queryTipoDireccionById(String idtipoDir) throws ApplicationException {
        TipoDireccion tipoDir = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("tdi2", idtipoDir);
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                tipoDir = new TipoDireccion();
                tipoDir.setTdiValor(obj.getString("VALOR"));
                tipoDir.setTdiUsuarioCreacion(obj.getString("USUARIO_CREACION"));
                String fecha_creacion = obj.getString("FECHA_CREACION");
                if (fecha_creacion != null) {
                    tipoDir.setTdiFechaCreacion(new Date(fecha_creacion));
                }
                tipoDir.setTdiUsuarioModificacion(obj.getString("USUARIO_EDICION"));
                String fecha_modificacion = obj.getString("FECHA_EDICION");
                if (fecha_modificacion != null) {
                    tipoDir.setTdiFechaModificacion(new Date(fecha_modificacion));
                }

            }
            return tipoDir;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error en queryTipoDireccionById. EX000 " + e.getMessage(), e);
        }
    }

    public Ubicacion queryUbicacionById(BigDecimal id) throws ApplicationException {
        Ubicacion ubicacion = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject objeto = adb.outDataObjec("ubi5", id.toString());
            DireccionUtil.closeConnection(adb);
            if (objeto != null) {
                ubicacion = new Ubicacion();
                BigDecimal id_ubi = (id);
                if (id_ubi != null) {
                    ubicacion.setUbiId(id_ubi);
                }
                ubicacion.setUbiNombre(objeto.getString("NOMBRE"));
                Geografico geo = new Geografico();
                GeograficoPolitico geo_politico = new GeograficoPolitico();
                BigDecimal geo_id = objeto.getBigDecimal("GEOGRAFICO_ID");
                if (geo_id != null) {
                    geo.setGeoId(geo_id);
                    ubicacion.setGeoId(geo);
                }
                BigDecimal gpo_id = objeto.getBigDecimal("GEOGRAFICO_POLITICO_ID");
                if (gpo_id != null) {
                    geo_politico.setGpoId(gpo_id);
                    ubicacion.setGpoId(geo_politico);
                }
                ubicacion.setUbiLatitud(objeto.getString("LATITUD"));
                ubicacion.setUbiLongitud(objeto.getString("LONGITUD"));
                ubicacion.setUbiEstadoRed(objeto.getString("ESTADO_RED"));
                String fecha = objeto.getString("FECHA_CREACION");
                if (fecha != null) {
                    ubicacion.setUbiFechaCreacion(new Date(fecha));
                }
                ubicacion.setUbiUsuarioCreacion(objeto.getString("USUARIO_CREACION"));
                String fecha_mod = objeto.getString("FECHA_EDICION");
                if (fecha_mod != null) {
                    ubicacion.setUbiFechaModificacion(new Date(fecha_mod));
                }
                ubicacion.setUbiUsuarioModificacion(objeto.getString("USUARIO_EDICION"));
                ubicacion.setUbiCuentaMatriz(objeto.getString("CUENTA_MATRIZ"));
            }
            return ubicacion;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error en queryUbicacionById. EX000 " + e.getMessage(), e);
        }
    }

    public String obtenerLocalidadDir(String dirId) throws ApplicationException {
        String localidadDir = "";
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject objeto = adb.outDataObjec("getLocalidadDir", dirId);
            DireccionUtil.closeConnection(adb);
            if (objeto != null) {
                localidadDir = (objeto.getString("LOCALIDAD"));
            }
            return localidadDir;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error en obtenerLocalidadDir. EX000 " + e.getMessage(), e);
        }
    }
}
