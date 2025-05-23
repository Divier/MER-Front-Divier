package co.com.claro.visitasTecnicas.business;

import co.com.claro.mgl.businessmanager.address.GeograficoPoliticoManager;
import co.com.claro.mgl.businessmanager.cm.CmtComunidadRrManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtComunidadRr;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.visitasTecnicas.entities.CityEntity;
import co.com.claro.visitasTecnicas.entities.DetalleDireccionEntity;
import co.com.claro.visitasTecnicas.entities.ParamMultivalor;
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

/**
 * Clase ParamMultivalor
 *
 * @author Diego Barrera
 * @version version 1.2
 * @date 2013/09/12
 *
 */
public class NegocioParamMultivalor {

    private static final Logger LOGGER = LogManager.getLogger(NegocioParamMultivalor.class);
    private static final String DESCRIPCION = "DESCRIPCION";
    private static final String ID_CON_CODDANEVT = "CONCODDANEVT";
    private static final String ID_CON_CITY_INFO_GEO = "CONCITYGEO";
    private static final String GEO_POLITICO_BY_GROUP = "GEOPOLBYGPOGEOID";
    private static final String GE_COUNT_SOLICITUDES_BY_DIR = "COUNTSOLBYDRDIR";
    private static final String GE_COUNT_SOLICITUDES_BY_DIR_CE = "COUNTSOLBYDRDIR2";
    private static final String CON_RR_CIUDAD = "CONRRCIUDAD";

    /**
     * Metodo que realiza la busqueda de los item para los combox
     *
     * @param idTipo
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @
     */
    public List<ParamMultivalor> consultaParametrosMultivalor(String idTipo) throws ApplicationException  {
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            List<ParamMultivalor> listParam = null;

            DataList list = adb.outDataList("dirParam", idTipo);

            if (list != null) {
                listParam = new ArrayList<ParamMultivalor>();
                for (DataObject obj : list.getList()) {
                    String idParametro = obj.getString(DESCRIPCION);
                    String descripcion = obj.getString(DESCRIPCION);

                    String tipo = obj.getString("ID_TIPO");
                    if (descripcion != null) {
                        ParamMultivalor param = new ParamMultivalor();
                        param.setIdParametro(idParametro);
                        param.setDescripcion(descripcion);
                        param.setIdTipo(tipo);
                        listParam.add(param);
                    }
                }
            }

            return listParam;
        } catch (ExceptionDB e) {
            LOGGER.error("Error realizando la consulta de parametros :" + e.getMessage(), e);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error realizando la consulta de parametros :" + e.getMessage(), e);
        } finally {
            DireccionUtil.closeConnection(adb);
        }
    }

    //@author Modificacion Juan David Hernandez
    public CityEntity consultaDptoCiudadVisor(String codCityVT) throws ApplicationException  {
        AccessData adb = null;
        try {
            CityEntity result = new CityEntity();

            //@author Modificacion Juan David Hernandez
            if (codCityVT != null && !codCityVT.isEmpty()) {
                CmtComunidadRrManager cmtComunidadRrManager = new CmtComunidadRrManager();
                GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
                CmtComunidadRr cmtComunidadRr = cmtComunidadRrManager.findComunidadByCodigo(codCityVT);
                if (cmtComunidadRr != null) {
                    GeograficoPoliticoMgl centroPoblado
                            = geograficoPoliticoManager.findById(cmtComunidadRr.getCiudad().getGpoId());
                    
                    if (centroPoblado != null && centroPoblado.getGeoCodigoDane() != null 
                            && !centroPoblado.getGeoCodigoDane().isEmpty()) {
                        adb = ManageAccess.createAccessData();

                        DataList listVT = adb.outDataList(ID_CON_CODDANEVT, centroPoblado.getGeoCodigoDane());
                        DireccionUtil.closeConnection(adb);
                        if (listVT != null) {
                            for (DataObject obj : listVT.getList()) {
                                String codDane = obj.getString("CODIGODANE");
                                if (codDane != null && !codDane.isEmpty()) {
                                    //los codigo dane deben ser de 8 digitos 
                                    // de no ser asi adicionamos un cero al principio
                                    while (codDane.length() < 8) {

                                        codDane = "0" + codDane;

                                    }
                                    result = consultaDptoCiudadGeo(codDane);
                                }
                            }
                        }
                        result.setCodCity(codCityVT);
                        return result;
                    }
                } else {
                    result.setCodCity(codCityVT);
                    return result;
                }
            }
                result.setCodCity(codCityVT);
                return result;

        } catch (ApplicationException | ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error en consultaDptoCiudadVisor. EX000 " + e.getMessage(), e);
        } 
    }
    
      public CityEntity consultaDptoCiudad(String codCityVT) throws ApplicationException  {
        AccessData adb = null;
        try {
            CityEntity result = new CityEntity();
            adb = ManageAccess.createAccessData();

            DataList listVT = adb.outDataList(ID_CON_CODDANEVT, codCityVT);
            DireccionUtil.closeConnection(adb);
            if (listVT != null) {
                for (DataObject obj : listVT.getList()) {
                    String codDane = obj.getString("CODIGODANE");
                    if (codDane != null && !codDane.isEmpty()) {
                        //los codigo dane deben ser de 8 digitos 
                        // de no ser asi adicionamos un cero al principio
                        while (codDane.length() < 8) {

                            codDane = "0" + codDane;

                        }
                        result = consultaDptoCiudadGeo(codDane);

                    }
                }
            }
            result.setCodCity(codCityVT);
            return result;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error en consultaDptoCiudad. EX000 " + e.getMessage(), e);
        }
    }

    public CityEntity consultaDptoCiudadGeo(String codDaneGeoPolitico) throws ApplicationException  {
        AccessData adb = null;
        try {
            CityEntity result = new CityEntity();
            adb = ManageAccess.createAccessData();
          
            DataList listVT = adb.outDataList(ID_CON_CITY_INFO_GEO, codDaneGeoPolitico);
            DireccionUtil.closeConnection(adb);
            if (listVT != null) {
                for (DataObject obj : listVT.getList()) {
                    result.setCityName(obj.getString("CITYNAME"));
                    result.setCityId(obj.getBigDecimal("CITYID"));
                    result.setDpto(obj.getString("DPTO"));
                    result.setPais(obj.getString("PAIS"));
                    result.setCodDane(codDaneGeoPolitico);
                }
                
            }
            
            return result;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error en consultaDptoCiudadGeo. EX000 " + e.getMessage(), e);
        }
    }

    public CityEntity consultaRRCiudadByCodCidadCodReg(String codCiudad, String CodRegional) throws ApplicationException  {
        AccessData adb = null;
        try {
            CityEntity result = new CityEntity();
            adb = ManageAccess.createAccessData();

            DataList listVT = adb.outDataList(CON_RR_CIUDAD, codCiudad, CodRegional);
            DireccionUtil.closeConnection(adb);
            if (listVT != null) {
                for (DataObject obj : listVT.getList()) {
                    result.setCityName(obj.getString("NOMBRE"));
                    result.setPostalCode(obj.getString("CPOSTAL"));
                }
            }
            return result;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error en consultaRRCiudadByCodCidadCodReg. EX000 " + e.getMessage(), e);
        }
    }

    public boolean UpdateSolicitudInstdrDireccion(DetalleDireccionEntity detalleDireccionEntity) throws ApplicationException  {

        String[] arg = getStringArrayDetalleDir(detalleDireccionEntity);
        boolean message = processIUD("INSTDRDIRECCION", arg);

        return message;
    }

    public BigDecimal getIsSolicitudInProcess(String streetName, String houseNumber, String apartmentNumber, String comunidad) throws ApplicationException  {
        BigDecimal result = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList(GE_COUNT_SOLICITUDES_BY_DIR_CE, houseNumber, streetName, comunidad, apartmentNumber);
            if (list != null) {
                for (DataObject obj : list.getList()) {
                    BigDecimal solicitudes = obj.getBigDecimal("IDSOL");
                    result = solicitudes;
                }
            }
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error en getIsSolicitudInProcess. EX000 " + e.getMessage(), e);
        } finally {
            DireccionUtil.closeConnection(adb);
        }
        return result;
    }

    public BigDecimal getCountSolicitudByDrDireccion(DetalleDireccionEntity detalleDireccionEntity, String comunidad) throws ApplicationException  {
        BigDecimal result = BigDecimal.ZERO;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList(GE_COUNT_SOLICITUDES_BY_DIR,
                    "0", detalleDireccionEntity.getIdtipodireccion() == null || detalleDireccionEntity.getIdtipodireccion().isEmpty() ? "0" : "1", detalleDireccionEntity.getIdtipodireccion(),//ID_TIPO_DIRECCION
                    "0", detalleDireccionEntity.getTipoviaprincipal() == null || detalleDireccionEntity.getTipoviaprincipal().isEmpty() ? "0" : "1", detalleDireccionEntity.getTipoviaprincipal(),//TIPO_VIA_PRINCIPAL,
                    "0", detalleDireccionEntity.getNumviaprincipal() == null || detalleDireccionEntity.getNumviaprincipal().isEmpty() ? "0" : "1", detalleDireccionEntity.getNumviaprincipal(),//NUM_VIA_PRINCIPAL,
                    "0", detalleDireccionEntity.getLtviaprincipal() == null || detalleDireccionEntity.getLtviaprincipal().isEmpty() ? "0" : "1", detalleDireccionEntity.getLtviaprincipal(),//LT_VIA_PRINCIPAL,
                    "0", detalleDireccionEntity.getNlpostviap() == null || detalleDireccionEntity.getNlpostviap().isEmpty() ? "0" : "1", detalleDireccionEntity.getNlpostviap(),//NL_POST_VIA_P,
                    "0", detalleDireccionEntity.getBisviaprincipal() == null || detalleDireccionEntity.getBisviaprincipal().isEmpty() ? "0" : "1", detalleDireccionEntity.getBisviaprincipal(),//BIS_VIA_PRINCIPAL,
                    "0", detalleDireccionEntity.getCuadviaprincipal() == null || detalleDireccionEntity.getCuadviaprincipal().isEmpty() ? "0" : "1", detalleDireccionEntity.getCuadviaprincipal(),//CUAD_VIA_PRINCIPAL,
                    "0", detalleDireccionEntity.getTipoviageneradora() == null || detalleDireccionEntity.getTipoviageneradora().isEmpty() ? "0" : "1", detalleDireccionEntity.getTipoviageneradora(),//TIPO_VIA_GENERADORA,
                    "0", detalleDireccionEntity.getNumviageneradora() == null || detalleDireccionEntity.getNumviageneradora().isEmpty() ? "0" : "1", detalleDireccionEntity.getNumviageneradora(),//NUM_VIA_GENERADORA,
                    "0", detalleDireccionEntity.getLtviageneradora() == null || detalleDireccionEntity.getLtviageneradora().isEmpty() ? "0" : "1", detalleDireccionEntity.getLtviageneradora(),//LT_VIA_GENERADORA,
                    "0", detalleDireccionEntity.getNlpostviag() == null || detalleDireccionEntity.getNlpostviag().isEmpty() ? "0" : "1", detalleDireccionEntity.getNlpostviag(),//NL_POST_VIA_G,
                    "0", detalleDireccionEntity.getBisviageneradora() == null || detalleDireccionEntity.getBisviageneradora().isEmpty() ? "0" : "1", detalleDireccionEntity.getBisviageneradora(),//BIS_VIA_GENERADORA,
                    "0", detalleDireccionEntity.getCuadviageneradora() == null || detalleDireccionEntity.getCuadviageneradora().isEmpty() ? "0" : "1", detalleDireccionEntity.getCuadviageneradora(),//CUAD_VIA_GENERADORA,
                    "0", detalleDireccionEntity.getPlacadireccion() == null || detalleDireccionEntity.getPlacadireccion().isEmpty() ? "0" : "1", detalleDireccionEntity.getPlacadireccion(),//PLACA_DIRECCION,
                    "0", detalleDireccionEntity.getCptiponivel1() == null || detalleDireccionEntity.getCptiponivel1().isEmpty() ? "0" : "1", detalleDireccionEntity.getCptiponivel1(),//CP_TIPO_NIVEL1,
                    "0", detalleDireccionEntity.getCptiponivel2() == null || detalleDireccionEntity.getCptiponivel2().isEmpty() ? "0" : "1", detalleDireccionEntity.getCptiponivel2(),//CP_TIPO_NIVEL2,
                    "0", detalleDireccionEntity.getCptiponivel3() == null || detalleDireccionEntity.getCptiponivel3().isEmpty() ? "0" : "1", detalleDireccionEntity.getCptiponivel3(),//CP_TIPO_NIVEL3,
                    "0", detalleDireccionEntity.getCptiponivel4() == null || detalleDireccionEntity.getCptiponivel4().isEmpty() ? "0" : "1", detalleDireccionEntity.getCptiponivel4(),//CP_TIPO_NIVEL4,
                    "0", detalleDireccionEntity.getCptiponivel5() == null || detalleDireccionEntity.getCptiponivel5().isEmpty() ? "0" : "1", detalleDireccionEntity.getCptiponivel5(),//CP_TIPO_NIVEL5,
                    "0", detalleDireccionEntity.getCptiponivel6() == null || detalleDireccionEntity.getCptiponivel6().isEmpty() ? "0" : "1", detalleDireccionEntity.getCptiponivel6(),//CP_TIPO_NIVEL6,
                    "0", detalleDireccionEntity.getCpvalornivel1() == null || detalleDireccionEntity.getCpvalornivel1().isEmpty() ? "0" : "1", detalleDireccionEntity.getCpvalornivel1(),//CP_VALOR_NIVEL1,
                    "0", detalleDireccionEntity.getCpvalornivel2() == null || detalleDireccionEntity.getCpvalornivel2().isEmpty() ? "0" : "1", detalleDireccionEntity.getCpvalornivel2(),//CP_VALOR_NIVEL2,
                    "0", detalleDireccionEntity.getCpvalornivel3() == null || detalleDireccionEntity.getCpvalornivel3().isEmpty() ? "0" : "1", detalleDireccionEntity.getCpvalornivel3(),//CP_VALOR_NIVEL3,
                    "0", detalleDireccionEntity.getCpvalornivel4() == null || detalleDireccionEntity.getCpvalornivel4().isEmpty() ? "0" : "1", detalleDireccionEntity.getCpvalornivel4(),//CP_VALOR_NIVEL4,
                    "0", detalleDireccionEntity.getCpvalornivel5() == null || detalleDireccionEntity.getCpvalornivel5().isEmpty() ? "0" : "1", detalleDireccionEntity.getCpvalornivel5(),//CP_VALOR_NIVEL5,
                    "0", detalleDireccionEntity.getCpvalornivel6() == null || detalleDireccionEntity.getCpvalornivel6().isEmpty() ? "0" : "1", detalleDireccionEntity.getCpvalornivel6(),//CP_VALOR_NIVEL6,
                    "0", detalleDireccionEntity.getMztiponivel1() == null || detalleDireccionEntity.getMztiponivel1().isEmpty() ? "0" : "1", detalleDireccionEntity.getMztiponivel1(),//MZ_TIPO_NIVEL1,
                    "0", detalleDireccionEntity.getMztiponivel2() == null || detalleDireccionEntity.getMztiponivel2().isEmpty() ? "0" : "1", detalleDireccionEntity.getMztiponivel2(),//MZ_TIPO_NIVEL2,
                    "0", detalleDireccionEntity.getMztiponivel3() == null || detalleDireccionEntity.getMztiponivel3().isEmpty() ? "0" : "1", detalleDireccionEntity.getMztiponivel3(),//MZ_TIPO_NIVEL3,
                    "0", detalleDireccionEntity.getMztiponivel4() == null || detalleDireccionEntity.getMztiponivel4().isEmpty() ? "0" : "1", detalleDireccionEntity.getMztiponivel4(),//MZ_TIPO_NIVEL4,
                    "0", detalleDireccionEntity.getMztiponivel5() == null || detalleDireccionEntity.getMztiponivel5().isEmpty() ? "0" : "1", detalleDireccionEntity.getMztiponivel5(),//MZ_TIPO_NIVEL5,
                    "0", detalleDireccionEntity.getMzvalornivel1() == null || detalleDireccionEntity.getMzvalornivel1().isEmpty() ? "0" : "1", detalleDireccionEntity.getMzvalornivel1(),//MZ_VALOR_NIVEL1,
                    "0", detalleDireccionEntity.getMzvalornivel2() == null || detalleDireccionEntity.getMzvalornivel2().isEmpty() ? "0" : "1", detalleDireccionEntity.getMzvalornivel2(),//MZ_VALOR_NIVEL2,
                    "0", detalleDireccionEntity.getMzvalornivel3() == null || detalleDireccionEntity.getMzvalornivel3().isEmpty() ? "0" : "1", detalleDireccionEntity.getMzvalornivel3(),//MZ_VALOR_NIVEL3,
                    "0", detalleDireccionEntity.getMzvalornivel4() == null || detalleDireccionEntity.getMzvalornivel4().isEmpty() ? "0" : "1", detalleDireccionEntity.getMzvalornivel4(),//MZ_VALOR_NIVEL4,
                    "0", detalleDireccionEntity.getMzvalornivel5() == null || detalleDireccionEntity.getMzvalornivel5().isEmpty() ? "0" : "1", detalleDireccionEntity.getMzvalornivel5(),//MZ_VALOR_NIVEL5
                    "0", detalleDireccionEntity.getIdDirCatastro() == null || detalleDireccionEntity.getIdDirCatastro().isEmpty() ? "0" : "1", detalleDireccionEntity.getIdDirCatastro(),//ID_DIR_CATASTRO
                    comunidad);
            if (list != null) {

                for (DataObject obj : list.getList()) {
                    BigDecimal solicitudes = obj.getBigDecimal("IDSOL");
                    result = solicitudes;

                }
            }
        } catch (ExceptionDB e) {
            result = BigDecimal.ZERO;
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error realizando la consulta del numero de solicitudes: " + e.getMessage(), e);
        } finally {
            DireccionUtil.closeConnection(adb);
        }
        return result;
    }

    private String[] getStringArrayDetalleDir(DetalleDireccionEntity detalleDireccionEntity) {

        String[] args = {
            //Si se desea adicionar el ID, podemos adicionar: detalleDireccionEntity.getId().toString(), //ID,
            detalleDireccionEntity.getIdDirCatastro(),//ID_DIR_CATASTRO
            detalleDireccionEntity.getIdsolicitud().toString(),//ID_SOLICITUD, 
            detalleDireccionEntity.getIdtipodireccion(),//ID_TIPO_DIRECCION,

            /**
             * Direccion Generadora de Calle - carrera*
             */
            detalleDireccionEntity.getDirprincalt(),//DIR_PRINC_ALT,
            detalleDireccionEntity.getBarrio(),// BARRIO,
            detalleDireccionEntity.getTipoviaprincipal(),//TIPO_VIA_PRINCIPAL,
            detalleDireccionEntity.getNumviaprincipal(),//NUM_VIA_PRINCIPAL,
            detalleDireccionEntity.getLtviaprincipal(),//LT_VIA_PRINCIPAL,
            detalleDireccionEntity.getNlpostviap(),//NL_POST_VIA_P,
            detalleDireccionEntity.getBisviaprincipal(),//BIS_VIA_PRINCIPAL,
            detalleDireccionEntity.getCuadviaprincipal(),//CUAD_VIA_PRINCIPAL,
            detalleDireccionEntity.getTipoviageneradora(),//TIPO_VIA_GENERADORA,
            detalleDireccionEntity.getNumviageneradora(),//NUM_VIA_GENERADORA,
            detalleDireccionEntity.getLtviageneradora(),//LT_VIA_GENERADORA,
            detalleDireccionEntity.getNlpostviag(),//NL_POST_VIA_G,
            detalleDireccionEntity.getBisviageneradora(),//BIS_VIA_GENERADORA,
            detalleDireccionEntity.getCuadviageneradora(),//CUAD_VIA_GENERADORA,
            detalleDireccionEntity.getPlacadireccion(),//PLACA_DIRECCION,

            /**
             * Complemeto o intraduciple *
             */
            detalleDireccionEntity.getCptiponivel1(),//CP_TIPO_NIVEL1,
            detalleDireccionEntity.getCptiponivel2(),//CP_TIPO_NIVEL2,
            detalleDireccionEntity.getCptiponivel3(),//CP_TIPO_NIVEL3,
            detalleDireccionEntity.getCptiponivel4(),//CP_TIPO_NIVEL4,
            detalleDireccionEntity.getCptiponivel5(),//CP_TIPO_NIVEL5,
            detalleDireccionEntity.getCptiponivel6(),//CP_TIPO_NIVEL6,
            detalleDireccionEntity.getCpvalornivel1(),//CP_VALOR_NIVEL1,
            detalleDireccionEntity.getCpvalornivel2(),//CP_VALOR_NIVEL2,
            detalleDireccionEntity.getCpvalornivel3(),//CP_VALOR_NIVEL3,
            detalleDireccionEntity.getCpvalornivel4(),//CP_VALOR_NIVEL4,
            detalleDireccionEntity.getCpvalornivel5(),//CP_VALOR_NIVEL5,
            detalleDireccionEntity.getCpvalornivel6(),//CP_VALOR_NIVEL6,

            /**
             * Direccion Manzana-Casa*
             */
            detalleDireccionEntity.getMztiponivel1(),//MZ_TIPO_NIVEL1,
            detalleDireccionEntity.getMztiponivel2(),//MZ_TIPO_NIVEL2,
            detalleDireccionEntity.getMztiponivel3(),//MZ_TIPO_NIVEL3,
            detalleDireccionEntity.getMztiponivel4(),//MZ_TIPO_NIVEL4,
            detalleDireccionEntity.getMztiponivel5(),//MZ_TIPO_NIVEL5,
            detalleDireccionEntity.getMzvalornivel1(),//MZ_VALOR_NIVEL1,
            detalleDireccionEntity.getMzvalornivel2(),//MZ_VALOR_NIVEL2,
            detalleDireccionEntity.getMzvalornivel3(),//MZ_VALOR_NIVEL3,
            detalleDireccionEntity.getMzvalornivel4(),//MZ_VALOR_NIVEL4,
            detalleDireccionEntity.getMzvalornivel5(),//MZ_VALOR_NIVEL5
            //nuevos elementos de la direccion intraducible
            detalleDireccionEntity.getMztiponivel6(),//MZ_TIPO_NIVEL6
            detalleDireccionEntity.getMzvalornivel6(),//MZ_VALOR_NIVEL6
            detalleDireccionEntity.getItTipoPlaca(),//IT_TIPO_PLACA
            detalleDireccionEntity.getItValorPlaca(),//IT_VALOR_PLACA
            detalleDireccionEntity.getEstrato(),//ESTRATO
            detalleDireccionEntity.getEstadoDir()//ESTADO_DIR
            //Nuevo campo Letra3
            ,detalleDireccionEntity.getLetra3g()
        };

        return args;
    }

    /**
     * Metodo que realiza insert y update segun un ctl_sql de la tabla SQLDATA
     *
     * @param ctl_sql
     * @param parameter
     * @return
     * @
     */
    private boolean processIUD(String ctl_sql, String parameter[]) throws ApplicationException  {
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            boolean ok = adb.in(ctl_sql, (Object[]) parameter);
            DireccionUtil.closeConnection(adb);
            if (ok) {
                return true;
            }
        } catch (ExceptionDB e) {
            LOGGER.error("Se genera error al intentar realizar : " + ctl_sql + " de la tabla SQLDATA ", e);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Se genera error al intentar realizar : " + ctl_sql + " de la tabla SQLDATA ", e);
        }
        return false;
    }

    public List<ParamMultivalor> getListElementByGroupId(String idGrupo) throws ApplicationException  {
        AccessData adb = null;
        try {
            List<ParamMultivalor> listParam = null;
            adb = ManageAccess.createAccessData();

            DataList list = adb.outDataList(GEO_POLITICO_BY_GROUP, idGrupo);
            DireccionUtil.closeConnection(adb);

            if (list != null) {
                listParam = new ArrayList<>();
                for (DataObject obj : list.getList()) {
                    BigDecimal idParametro = obj.getBigDecimal("ID");
                    BigDecimal tipo = obj.getBigDecimal("ID_GRUPO");
                    String descripcion = obj.getString("NOMBRE");
                    if (descripcion != null && idParametro != null) {
                        ParamMultivalor param = new ParamMultivalor();
                        param.setIdParametro(idParametro.toBigInteger().toString());
                        param.setDescripcion(descripcion);
                        param.setIdTipo(tipo.toBigInteger().toString());
                        listParam.add(param);
                    }
                }
            }
            return listParam;
        } catch (ExceptionDB e) {
            LOGGER.error("Error realizando la consulta de Geografico_politico: " + e.getMessage(), e);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error realizando la consulta de Geografico_politico: " + e.getMessage(), e);
        }

    }

    public String getMensajeBodyMail(String respuestaModulo) throws ApplicationException  {
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject result = adb.outDataObjec("getMesajeMail", "RPT-" + respuestaModulo);
            DireccionUtil.closeConnection(adb);
            if (result != null) {
                return result.getString(DESCRIPCION);
            }
        } catch (ExceptionDB e) {
            LOGGER.error("Error realizando la consulta de parametros :" + e.getMessage(), e);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error realizando la consulta de parametros :" + e.getMessage(), e);
        }
        return " ";
    }
}
