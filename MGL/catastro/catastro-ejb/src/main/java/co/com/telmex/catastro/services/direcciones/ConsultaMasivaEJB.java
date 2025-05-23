package co.com.telmex.catastro.services.direcciones;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.telmex.catastro.data.Nodo;
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
 * Clase ConsultaMasivaEJB implementa ConsultaMasivaEJBRemote
 *
 * @author Deiver Rovira.
 * @version	1.0
 * @version	1.1 - Modificado por: Direcciones face I Carlos Vilamil 2013-05-11
 */
@Stateless(name = "consultaMasivaEJB", mappedName = "consultaMasivaEJB", description = "cConsultaMasiva")
@Remote({ConsultaMasivaEJBRemote.class})
public class ConsultaMasivaEJB implements ConsultaMasivaEJBRemote {

    private static final Logger LOGGER = LogManager.getLogger(ConsultaMasivaEJB.class);

    /**
     *
     * @param calle
     * @param cod_ciudad
     * @param cantMaxregistros
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public List<ConsultaMasivaTable> queryConsultaMasivaPorCruce(String calle, String cod_ciudad, String cantMaxregistros) throws ApplicationException {
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("dir9", "%"+calle+"%", cod_ciudad, cantMaxregistros);

            DireccionUtil.closeConnection(adb);
            //Cambios para reciclaje de código y funcionalidad de consultas masivas

            return Utilidades.fillTable(list);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar por cruce. EX000 " + e.getMessage(), e);
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
     * @param cod_ciudad
     * @param cantMaxregistros
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public List<ConsultaMasivaTable> queryConsultaMasivaPorPropiedades(String nodo, String ctaMatriz, String tipoRed, String estrato,
            String nivelSocio, String barrio, String cod_ciudad, String cantMaxregistros) throws ApplicationException {
        List<ConsultaMasivaTable> table = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            boolean validarBarrio = true;
            if (barrio.equals("G.GEO_NOMBRE")) {
                validarBarrio = false;
            }
            /* Camilo E. Gaviria - 2013-01-17
             * Esta expresión se modifico para inhabilitar los campos respectivos a HHPP
             DataList list = adb.outDataList("sql1", nodo, nodo, ctaMatriz,ctaMatriz, 
             tipoRed,tipoRed, estrato,estrato, nivelSocio,nivelSocio, cod_ciudad, cantMaxregistros);
             */

            //Cambios para reciclaje de código y funcionalidad de consultas especificas Ivan Turriago

            String nodoUno = nodo.equals(Constantes.D_DIR_NODOUNO) ? nodo : "'" + nodo + "'";
            String nodoDos = ctaMatriz.equals(Constantes.D_DIR_NODODOS) ? ctaMatriz : "'" + ctaMatriz + "'";
            String nodoTres = tipoRed.equals(Constantes.D_DIR_NODOTRES) ? tipoRed : "'" + tipoRed + "'";

            DataList list = adb.outDataList("sql8", estrato, nivelSocio, nodoUno,
                    nodoDos, nodoTres, cod_ciudad, cantMaxregistros);

            DireccionUtil.closeConnection(adb);

            table = Utilidades.fillTable(list);


            return Utilidades.validarBarrio(table, validarBarrio, barrio);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al consultar por propieadades. EX000 " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param id
     * @param sentence
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public boolean insertarQuery(String id, String sentence) throws ApplicationException {
        boolean rta = false;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            rta = adb.in("sql1", id, sentence);
            adb.commit();
            DireccionUtil.closeConnection(adb);
            return rta;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al agregar la consulta. EX000 " + e.getMessage(), e);
        }
    }

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
            throw new ApplicationException("Error al consultar los nodos. EX000 " + e.getMessage(), e);
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
            throw new ApplicationException("Error al consultar la cantidad máxima de registros según los parametros. EX000 " + e.getMessage(), e);
        }
    }
}
