package co.com.telmex.catastro.services.direcciones;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.visitasTecnicas.business.ConsultaEspecificaManager;
import co.com.claro.visitasTecnicas.business.NodoRR;
import co.com.telmex.catastro.data.Direccion;
import co.com.telmex.catastro.data.Nodo;
import co.com.telmex.catastro.data.SubDireccion;
import co.com.telmex.catastro.services.comun.Constantes;
import co.com.telmex.catastro.services.comun.Utilidades;
import co.com.telmex.catastro.services.util.ConsultaMasivaTable;
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
 * Clase ConsultaEspecificaEJB Implementa ConsultaEspecificaEJBRemote
 *
 * @author Deiver Rovira.
 * @version	1.0
 */
@Stateless(name = "consultaEspecificaEJB", mappedName = "consultaEspecificaEJB", description = "consultaEspecifica")
@Remote({ConsultaEspecificaEJBRemote.class})
public class ConsultaEspecificaEJB implements ConsultaEspecificaEJBRemote {

    private static final Logger LOGGER = LogManager.getLogger(ConsultaEspecificaEJB.class);

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     */
    @Override
    public List<Nodo> queryNodos() throws ApplicationException {
        List<Nodo> nodos = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("nod4");
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                nodos = new ArrayList<Nodo>();
                for (DataObject obj : list.getList()) {
                    Nodo nodo = new Nodo();

                    BigDecimal nodo_id = obj.getBigDecimal("NODO_ID");
                    String cod = obj.getString("NOD_CODIGO");
                    nodo.setNodId(nodo_id);
                    nodo.setNodCodigo(cod);
                    nodos.add(nodo);
                }
            }
            return nodos;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar nodos. EX000 " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param nodo
     * @param ctaMatriz
     * @param tipoRed
     * @param estrato
     * @param nivelSocio
     * @param barrio
     * @param direccion
     * @param codCiudad
     * @param maxCantRegistros
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public List<ConsultaMasivaTable> queryConsultaEspecificaFiltroEmpiezaPor(String nodo, String ctaMatriz, String tipoRed, String estrato,
            String nivelSocio, String barrio, String direccion, String codCiudad, String maxCantRegistros) throws ApplicationException {

        //Cambios para reciclaje de código y funcionalidad de consultas especificas
        return doConsultar("sql10", nodo, ctaMatriz, tipoRed, estrato, nivelSocio, barrio, direccion+"%", codCiudad, maxCantRegistros);

    }

    /**
     *
     * @param nodo
     * @param ctaMatriz
     * @param tipoRed
     * @param estrato
     * @param nivelSocio
     * @param barrio
     * @param direccion
     * @param codCiudad
     * @param maxCantRegistros
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public List<ConsultaMasivaTable> queryConsultaEspecificaFiltroTerminaCon(String nodo, String ctaMatriz, String tipoRed, String estrato, String nivelSocio,
            String barrio, String direccion, String codCiudad, String maxCantRegistros) throws ApplicationException {
        //Cambios para reciclaje de código y funcionalidad de consultas especificas
        return doConsultar("sql11", nodo, ctaMatriz, tipoRed, estrato, nivelSocio, barrio, "%"+direccion, codCiudad, maxCantRegistros);

    }

    /**
     *
     * @param nodo
     * @param ctaMatriz
     * @param tipoRed
     * @param estrato
     * @param nivelSocio
     * @param barrio
     * @param direccion
     * @param codCiudad
     * @param maxCantRegistros
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public List<ConsultaMasivaTable> queryConsultaEspecificaFiltroNoContiene(String nodo, String ctaMatriz, String tipoRed, String estrato,
            String nivelSocio, String barrio, String direccion, String codCiudad, String maxCantRegistros) throws ApplicationException {
        //Cambios para reciclaje de código y funcionalidad de consultas especificas
        return doConsultar("sql12", nodo, ctaMatriz, tipoRed, estrato, nivelSocio, barrio, "%"+direccion+"%", codCiudad, maxCantRegistros);

    }

    /**
     *
     * @param nodo
     * @param ctaMatriz
     * @param tipoRed
     * @param estrato
     * @param nivelSocio
     * @param barrio
     * @param direccion
     * @param codCiudad
     * @param maxCantRegistros
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public List<ConsultaMasivaTable> queryConsultaEspecificaFiltroEsIgualA(String nodo, String ctaMatriz, String tipoRed, String estrato,
            String nivelSocio, String barrio, String direccion, String codCiudad, String maxCantRegistros) throws ApplicationException {
        //Cambios para reciclaje de código y funcionalidad de consultas especificas
        return doConsultar("sql13", nodo, ctaMatriz, tipoRed, estrato, nivelSocio, barrio, direccion, codCiudad, maxCantRegistros);

    }

    /**
     *
     * @param nodo
     * @param ctaMatriz
     * @param tipoRed
     * @param estrato
     * @param nivelSocio
     * @param barrio
     * @param direccion
     * @param codCiudad
     * @param maxCantRegistros
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public List<ConsultaMasivaTable> queryConsultaEspecificaFiltroComodin(String nodo, String ctaMatriz, String tipoRed, String estrato,
            String nivelSocio, String barrio, String direccion, String codCiudad, String maxCantRegistros) throws ApplicationException {
        //Cambios para reciclaje de código y funcionalidad de consultas especificas
        return doConsultar("sql14", nodo, ctaMatriz, tipoRed, estrato, nivelSocio, barrio, direccion, codCiudad, maxCantRegistros);

    }

    /**
     *
     * @param igac
     * @param idCiudad
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public List<Direccion> queryAddressOnRepoByIgac(String igac, String idCiudad) throws ApplicationException {
        List<Direccion> direcciones = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("dir12", igac+"%", idCiudad);
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                direcciones = new ArrayList<Direccion>();
                for (DataObject obj : list.getList()) {
                    String dir_igac = obj.getString("DIR_FORMATO_IGAC");
                    Direccion dir = new Direccion();
                    dir.setDirFormatoIgac(dir_igac);
                    direcciones.add(dir);
                }
            }
            return direcciones;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al cosultar la dirección por IGAC. EX000 " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param idDireccion
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public Direccion queryAddressOnRepoById(String idDireccion) throws ApplicationException {
        ConsultaEspecificaManager manager = new ConsultaEspecificaManager();
        return manager.queryAddressOnRepoById(idDireccion);
    }

    @Override
    public Direccion queryAddressOnRepoBySubDir(String idDireccion) throws ApplicationException {
        ConsultaEspecificaManager manager = new ConsultaEspecificaManager();
        SubDireccion subDireccion = manager.querySubAddressOnRepositoryById(new BigDecimal(idDireccion));
        return manager.queryAddressOnRepoById(subDireccion.getDireccion().getDirId().toString());
    }

    private String queryGeograficoNameById(String idGeoLocalidad) throws ApplicationException {
        String geoName = "";
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("geo9", idGeoLocalidad);
            //Select geo_nombre NOMBRE_GEO where id= '?'
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                geoName = obj.getString("NOMBRE_GEO");
            }
            return geoName;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error eal consultar geografico por id. EX000 " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param acronimo
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public int queryCantMaxRegistrosFromParametros(String acronimo) throws ApplicationException {
        int nivelConfiabilidad = 95;
        String nivelConf = "";
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("par1", acronimo);
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                nivelConf = obj.getString("PAR_VALOR");
                nivelConfiabilidad = Integer.valueOf(nivelConf);
            }
            return nivelConfiabilidad;
        } catch (ExceptionDB | NumberFormatException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error eal consultar cantidad máxima de registros segun parámetros. EX000 " + e.getMessage(), e);
        }
    }

    //Cambios para reciclaje de código y funcionalidad de consultas especificas
    private List<ConsultaMasivaTable> doConsultar(String idConsulta, String nodo, String ctaMatriz, String tipoRed, String estrato,
            String nivelSocio, String barrio, String direccion, String codCiudad, String maxCantRegistros) throws ApplicationException {
        List<ConsultaMasivaTable> table = null;
        DataList list = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();

            boolean validarBarrio = true;
            if (barrio.equals("G.GEO_NOMBRE")) {
                validarBarrio = false;
            }

            /* Camilo Gaviria - se inactiva para inhabilitar parametros HHPP
             DataList list = adb.outDataList("sql3", nodo, nodo, ctaMatriz, ctaMatriz, tipoRed, tipoRed,
             estrato,estrato, nivelSocio,nivelSocio, direccion+"%", codCiudad, maxCantRegistros);
             */
            String nodoUno = nodo.equals(Constantes.D_DIR_NODOUNO) ? nodo : "'" + nodo + "'";
            String nodoDos = ctaMatriz.equals(Constantes.D_DIR_NODODOS) ? ctaMatriz : "'" + ctaMatriz + "'";
            String nodoTres = tipoRed.equals(Constantes.D_DIR_NODOTRES) ? tipoRed : "'" + tipoRed + "'";

            list = adb.outDataList(idConsulta, estrato, estrato, nivelSocio, nivelSocio,
                    nodoUno, nodoDos, nodoTres, direccion, codCiudad, maxCantRegistros);
            DireccionUtil.closeConnection(adb);
            table = Utilidades.fillTable(list);
            return Utilidades.validarBarrio(table, validarBarrio, barrio);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al realizar la consulta. EX000 " + e.getMessage(), e);
        }

    }

    @Override
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
            throw new ApplicationException("Error al traer el nodo RR por codigo de cidudad. EX000 " + e.getMessage(), e);
        }
    }
}
