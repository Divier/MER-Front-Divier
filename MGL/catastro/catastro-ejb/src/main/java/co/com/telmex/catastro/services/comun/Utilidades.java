/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.telmex.catastro.services.comun;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.visitasTecnicas.entities.DetalleDireccionEntity;
import co.com.telmex.catastro.services.util.ConsultaMasivaTable;
import co.com.telmex.catastro.services.util.DireccionUtil;
import co.com.telmex.catastro.services.util.Parametros;
import co.com.telmex.catastro.services.util.ResourceEJB;
import com.jlcg.db.AccessData;
import com.jlcg.db.data.DataList;
import com.jlcg.db.data.DataObject;
import com.jlcg.db.exept.ExceptionDB;
import com.jlcg.db.sql.ManageAccess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author user
 */
public final class Utilidades {

    private static final Logger LOGGER = LogManager.getLogger(Utilidades.class);

    public static List<ConsultaMasivaTable> fillTable(DataList list) throws ApplicationException {
        List<ConsultaMasivaTable> table = new ArrayList<ConsultaMasivaTable>();
        if (list != null) {
            for (DataObject obj : list.getList()) {
                BigDecimal dir_id = obj.getBigDecimal("DIR_ID");
                BigDecimal hhpp_id = obj.getBigDecimal("HHPP");
                String ciudad = obj.getString("CIUDAD");
                BigDecimal geo_id = obj.getBigDecimal("GEO_ID");
                BigDecimal geo_geo_id = obj.getBigDecimal("GEO_GEO_ID");
                String nombreGeo = obj.getString("NOMBRE_GEO");
                BigDecimal tipoGeo = obj.getBigDecimal("T_GEO");
                String direccion = obj.getString("DIRECCION");
                String ctaMatriz = obj.getString("CTAMATRIZ");
                String tipoRed = obj.getString("TIPORED");
                String nodo = obj.getString("NODO");
                String estado_hhpp = obj.getString("ESTADO");
                String area = obj.getString("AREA");
                String distrito = obj.getString("DISTRITO");
                String divisional = obj.getString("DIVISIONAL");
                String unidad_gestion = obj.getString("UNDGESTION");
                String zona = obj.getString("ZONA");
                String nodoUno = obj.getString("DIR_NODOUNO");//Direcciones face I Carlos Vilamil 2013-05-24 V 1.1
                String nodoDos = obj.getString("DIR_NODODOS");//Direcciones face I Carlos Vilamil 2013-05-24 V 1.1
                String nodoTres = obj.getString("DIR_NODOTRES");//Direcciones face I Carlos Vilamil 2013-05-24 V 1.1
                String cmDirManzanaCatastral = obj.getString(Constantes.DIR_MANZANA_CATASTRAL);
                String actividadEconomica = obj.getString(Constantes.DIR_ACTIVIDAD_ECONOMICA);
                String existencia = obj.getString(Constantes.DIR_REVISAR);
                String direccionAlterna = obj.getString(Constantes.DIR_ALTERNA);
                String nivelSocioEcono = obj.getBigDecimal(Constantes.NVLS) == null
                        ? "" : String.valueOf(obj.getBigDecimal(Constantes.NVLS));
                String nivelConfiabilidad = obj.getBigDecimal(Constantes.DIR_CONFIABILIDAD)
                        == null ? "" : String.valueOf(obj.getBigDecimal(Constantes.DIR_CONFIABILIDAD));
                String estrato = obj.getBigDecimal(Constantes.DIR_ESTRATO) == null
                        ? "" : String.valueOf(obj.getBigDecimal(Constantes.DIR_ESTRATO));

                ConsultaMasivaTable consultaTable = new ConsultaMasivaTable();
                if (tipoGeo != null) {
                    if (tipoGeo.toString().equals("3")) { // Modificar de acuerdo a DB
                        //Es una manzana
                        if (geo_geo_id != null) {
                            queryBarrioAndLocalidadName(geo_geo_id.toString(), consultaTable);
                        }
                    }
                    if (tipoGeo.toString().equals("2")) {// Modificar de acuerdo a DB
                        //Es un Barrio
                        consultaTable.setCm_barrio(nombreGeo);
                        if (geo_geo_id != null) {
                            consultaTable.setCm_localidad(queryGeograficoNameById(geo_geo_id.toString()));
                        }
                    }
                    if (tipoGeo.toString().equals("1")) {// Modificar de acuerdo a DB
                        //Es una localidad
                        consultaTable.setCm_localidad(nombreGeo);
                        consultaTable.setCm_barrio("");
                    }
                }
                consultaTable.setCm_ciudad(ciudad);
                consultaTable.setCm_cuentaMatriz(ctaMatriz);
                consultaTable.setCm_direccion(direccion);
                consultaTable.setCm_estadoHhpp(estado_hhpp);
                consultaTable.setCm_flagHhpp((hhpp_id == null) ? "NO" : "SI");
                consultaTable.setCm_nodo(nodo);
                consultaTable.setCm_nodoArea(area);
                consultaTable.setCm_nodoDistrito(distrito);
                consultaTable.setCm_nodoDivisional(divisional);
                consultaTable.setCm_nodoUnidadGestion(unidad_gestion);
                consultaTable.setCm_nodoZona(zona);
                consultaTable.setCm_tipoRed(tipoRed);
                consultaTable.setNodoUno(nodoUno);//Direcciones face I Carlos Vilamil 2013-05-24 V 1.1
                consultaTable.setNodoDos(nodoDos);//Direcciones face I Carlos Vilamil 2013-05-24 V 1.1
                consultaTable.setNodoTres(nodoTres);//Direcciones face I Carlos Vilamil 2013-05-24 V 1.1
                consultaTable.setCmDirManzanaCatastral(cmDirManzanaCatastral);
                consultaTable.setActividadEconomica(actividadEconomica);
                consultaTable.setExistencia(existencia);
                consultaTable.setDireccionAlterna(direccionAlterna);
                consultaTable.setCm_nivelSocio(nivelSocioEcono);
                consultaTable.setCm_confiabilidad(nivelConfiabilidad);
                consultaTable.setCm_estrato(estrato);
                table.add(consultaTable);

            }
        }
        return table;
    }

    public static List<ConsultaMasivaTable> fillTableOrigen(DataList list) throws ApplicationException {
        List<ConsultaMasivaTable> table = new ArrayList<ConsultaMasivaTable>();
        if (list != null) {
            for (DataObject obj : list.getList()) {
                BigDecimal dir_id = obj.getBigDecimal("DIR_ID");
                BigDecimal hhpp_id = obj.getBigDecimal("HHPP");
                String ciudad = obj.getString("CIUDAD");
                BigDecimal geo_id = obj.getBigDecimal("GEO_ID");
                BigDecimal geo_geo_id = obj.getBigDecimal("GEO_GEO_ID");
                String nombreGeo = obj.getString("NOMBRE_GEO");
                BigDecimal tipoGeo = obj.getBigDecimal("T_GEO");
                String direccion = obj.getString("DIRECCION");
                String ctaMatriz = obj.getString("CTAMATRIZ");
                String tipoRed = obj.getString("TIPORED");
                String nodo = obj.getString("NODO");
                String estado_hhpp = obj.getString("ESTADO");
                String area = obj.getString("AREA");
                String distrito = obj.getString("DISTRITO");
                String divisional = obj.getString("DIVISIONAL");
                String unidad_gestion = obj.getString("UNDGESTION");
                String zona = obj.getString("ZONA");
                String nodoUno = obj.getString("DIR_NODOUNO");//Direcciones face I Carlos Vilamil 2013-05-24 V 1.1
                String nodoDos = obj.getString("DIR_NODODOS");//Direcciones face I Carlos Vilamil 2013-05-24 V 1.1
                String nodoTres = obj.getString("DIR_NODOTRES");//Direcciones face I Carlos Vilamil 2013-05-24 V 1.1
                String cmDirManzanaCatastral = obj.getString(Constantes.DIR_MANZANA_CATASTRAL);
                String actividadEconomica = obj.getString(Constantes.DIR_ACTIVIDAD_ECONOMICA);
                String existencia = obj.getString(Constantes.DIR_REVISAR);
                String direccionAlterna = obj.getString(Constantes.DIR_ALTERNA);
                String nivelSocioEcono = obj.getBigDecimal(Constantes.NVLS) == null
                        ? "" : String.valueOf(obj.getBigDecimal(Constantes.NVLS));
                String nivelConfiabilidad = obj.getBigDecimal(Constantes.DIR_CONFIABILIDAD)
                        == null ? "" : String.valueOf(obj.getBigDecimal(Constantes.DIR_CONFIABILIDAD));
                String estrato = obj.getBigDecimal(Constantes.DIR_ESTRATO) == null
                        ? "" : String.valueOf(obj.getBigDecimal(Constantes.DIR_ESTRATO));
                String origen = obj.getString(Constantes.DIR_ORIGEN);

                ConsultaMasivaTable consultaTable = new ConsultaMasivaTable();
                if (tipoGeo != null) {
                    if (tipoGeo.toString().equals("3")) { // Modificar de acuerdo a DB
                        //Es una manzana
                        if (geo_geo_id != null) {
                            queryBarrioAndLocalidadName(geo_geo_id.toString(), consultaTable);
                        }
                    }
                    if (tipoGeo.toString().equals("2")) {// Modificar de acuerdo a DB
                        //Es un Barrio
                        consultaTable.setCm_barrio(nombreGeo);
                        if (geo_geo_id != null) {
                            consultaTable.setCm_localidad(queryGeograficoNameById(geo_geo_id.toString()));
                        }
                    }
                    if (tipoGeo.toString().equals("1")) {// Modificar de acuerdo a DB
                        //Es una localidad
                        consultaTable.setCm_localidad(nombreGeo);
                        consultaTable.setCm_barrio("");
                    }
                }
                consultaTable.setCm_idDireccion(dir_id);
                consultaTable.setCm_ciudad(ciudad);
                consultaTable.setCm_cuentaMatriz(ctaMatriz);
                consultaTable.setCm_direccion(direccion);
                consultaTable.setCm_estadoHhpp(estado_hhpp);
                consultaTable.setCm_flagHhpp((hhpp_id == null) ? "NO" : "SI");
                consultaTable.setCm_nodo(nodo);
                consultaTable.setCm_nodoArea(area);
                consultaTable.setCm_nodoDistrito(distrito);
                consultaTable.setCm_nodoDivisional(divisional);
                consultaTable.setCm_nodoUnidadGestion(unidad_gestion);
                consultaTable.setCm_nodoZona(zona);
                consultaTable.setCm_tipoRed(tipoRed);
                consultaTable.setNodoUno(nodoUno);//Direcciones face I Carlos Vilamil 2013-05-24 V 1.1
                consultaTable.setNodoDos(nodoDos);//Direcciones face I Carlos Vilamil 2013-05-24 V 1.1
                consultaTable.setNodoTres(nodoTres);//Direcciones face I Carlos Vilamil 2013-05-24 V 1.1
                consultaTable.setCmDirManzanaCatastral(cmDirManzanaCatastral);
                consultaTable.setActividadEconomica(actividadEconomica);
                consultaTable.setExistencia((existencia == null || existencia.equals("0")) ? "NO" : "SI");
                consultaTable.setDireccionAlterna(direccionAlterna);
                consultaTable.setCm_nivelSocio(nivelSocioEcono);
                consultaTable.setCm_confiabilidad(nivelConfiabilidad);
                consultaTable.setCm_estrato(estrato);
                consultaTable.setCm_estrato(origen);
                table.add(consultaTable);
            }
        }
        return table;
    }

    private static void queryBarrioAndLocalidadName(String idGeoGeo, ConsultaMasivaTable consulta) throws ApplicationException {
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("geo8", idGeoGeo);
            String nombreLocalidad = "";
            //Select geo_id IDLOCALIDAD, geo_nombre NOMBRE_GEO where id= '?'
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                BigDecimal id_localidad = obj.getBigDecimal("IDLOCALIDAD");
                //Se asigna el barrio
                consulta.setCm_barrio(obj.getString("NOMBRE_GEO"));
                if (id_localidad != null) {
                    nombreLocalidad = queryGeograficoNameById(id_localidad.toString());
                    consulta.setCm_localidad(nombreLocalidad);
                }
            }
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(Utilidades.class)+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar el barrio. EX000: " + e.getMessage(), e);
        }
    }

    private static String queryGeograficoNameById(String idGeoLocalidad) throws ApplicationException {
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
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(Utilidades.class)+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar el nombre del geográfico. EX000: " + e.getMessage(), e);
        }
    }

    public static List<ConsultaMasivaTable> validarBarrio(
            List<ConsultaMasivaTable> table, boolean validarBarrio,
            String barrio) {

        if (validarBarrio) {
            List<ConsultaMasivaTable> tableAux = new ArrayList<ConsultaMasivaTable>();
            for (ConsultaMasivaTable consultaMasivaTable : table) {
                if (consultaMasivaTable.getCm_barrio().toUpperCase().contains(barrio.toUpperCase().replace("'", ""))) {
                    tableAux.add(consultaMasivaTable);
                }
            }
            return tableAux;
        }
        return table;
    }

    public static boolean validarMultiorigen(DetalleDireccionEntity direccion) throws ApplicationException {
        try {
            if (direccion != null
                    && direccion.getMultiOrigen() != null
                    && !direccion.getMultiOrigen().isEmpty()) {
                return direccion.getMultiOrigen().equalsIgnoreCase("1");
            }
            AccessData adb = null;
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("validarMutiOrigenRR", direccion.getIdsolicitud().toBigInteger().toString());
            DireccionUtil.closeConnection(adb);

            if (obj != null) {
                return obj.getString("GPO_MULTIORIGEN").equalsIgnoreCase("1");
            } else {
                throw new ApplicationException("No fue posible validar si la Ciudad es Multiorigen");
            }
        } catch (ExceptionDB ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(Utilidades.class)+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de validar la multiorigen. EX000: " + ex.getMessage(), ex);
        }

    }

    public static String queryParametrosConfig(String parametro) {
        ResourceEJB resourceEJB = new ResourceEJB();
        Parametros param = null;
        String paramStr = "";
        param = resourceEJB.queryParametros(parametro);
        if (param != null) {
            paramStr = param.getValor();
        }
        return paramStr;
    }

    /**
     * Método que cuenta columnas y filas de un CSV
     * @param archivo
     * @return int[]{totalFilas, totalColumnas}
     * @throws ApplicationException
     */
    public static int[] countRegsCsv(UploadedFile archivo) throws ApplicationException {

        int totalFilas = 0;
        int totalColumnas = 0;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(archivo.getInputStream())))
        {
            String line = br.readLine();
            if(null != line){
                totalColumnas = line.split(";").length;
                while (null != line) {
                    line = br.readLine();
                    totalFilas++;
                }
            }
        } catch (IOException e) {
            LOGGER.error("Ocurrió un error leyendo archivo CSV", e);
            throw new ApplicationException("Ocurrió un error leyendo archivo CSV");
        }
        return new int[]{totalFilas, totalColumnas};
    }
}
