package co.com.telmex.catastro.services.comun;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.telmex.catastro.data.CatalogAdc;
import co.com.telmex.catastro.data.CatalogFilter;
import co.com.telmex.catastro.data.CatalogFilterReport;
import co.com.telmex.catastro.data.DataMetaTable;
import co.com.telmex.catastro.data.DataRelaciones;
import co.com.telmex.catastro.data.DataResult;
import co.com.telmex.catastro.data.DataResultquery;
import co.com.telmex.catastro.data.DataRow;
import co.com.telmex.catastro.data.DataTable;
import co.com.telmex.catastro.services.seguridad.AuditoriaEJB;
import co.com.telmex.catastro.services.util.DireccionUtil;
import com.jlcg.db.AccessData;
import com.jlcg.db.data.DataList;
import com.jlcg.db.data.DataListIndex;
import com.jlcg.db.data.DataObject;
import com.jlcg.db.data.DataObjectIndex;
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
 * Clase CatalogEJB implementa CatalogEJBRemote
 *
 * @author Deiver Rovira.
 * @version	1.0
 */
@Stateless(name = "catalogEJB", mappedName = "catalogEJB", description = "catalogo")
@Remote({CatalogEJBRemote.class})
public class CatalogEJB implements CatalogEJBRemote {

    private static final Logger LOGGER = LogManager.getLogger(CatalogEJB.class);
    private static String MESSAGE_OK = "La operacion fue correcta.";
    private static String MESSAGE_ERROR = "Fallo en la operacion";

    /**
     *
     */
    public CatalogEJB() {
    }

    /**
     * Consulta lista de los catalogos configurados para el ususario Pagina (1)
     *
     * @param nameLogin
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public List<CatalogAdc> queryListCatalog(String nameLogin) throws ApplicationException {
        List<CatalogAdc> listCatalog = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("ctl1");
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                listCatalog = new ArrayList<CatalogAdc>();
                for (DataObject obj : list.getList()) {
                    BigDecimal ctl_id = obj.getBigDecimal("CTL_ID");
                    String ctl_name = obj.getString("CTL_NAME");
                    String ctl_sql = obj.getString("CTL_SQL");
                    String ctl_sqlcount = obj.getString("CTL_SQLCOUNT");

                    CatalogAdc catalogo = new CatalogAdc();
                    catalogo.setCtlId(ctl_id);
                    catalogo.setCtlName(ctl_name);
                    catalogo.setCtlSql(ctl_sql);
                    catalogo.setCtlSqlCount(ctl_sqlcount);
                    listCatalog.add(catalogo);
                }
            }
            return listCatalog;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar el listado de catálogos. EX000: " + e.getMessage(), e);
        }
    }

    /**
     * Consulta la configuracion para despligue de Catalogo en interfaz consulta
     * de registros.Define filtros y permisos de la consulta Pagina (2)
     *
     * @param ctl_id
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public CatalogAdc queryCatalog(BigDecimal ctl_id) throws ApplicationException {
        CatalogAdc catalog = null;

        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("ctl2", ctl_id.toString());
            if (obj != null) {
                catalog = new CatalogAdc();
                catalog.setCtlId(obj.getBigDecimal("CTL_ID"));
                catalog.setCtlName(obj.getString("CTL_NAME"));
                catalog.setCtlMode(obj.getString("CTL_MODE"));
                catalog.setCtlSql(obj.getString("CTL_SQL"));
                catalog.setCtlAlias(obj.getString("CTL_ALIAS"));
                catalog.setCtlBcreate(obj.getString("CTL_BCREATE"));
                catalog.setCtlProgramam(obj.getString("CDT_PROGRAM"));

                List<CatalogFilter> listfilter = null;
                DataList list = adb.outDataList("ctl3", ctl_id.toString());
                DireccionUtil.closeConnection(adb);
                if (list != null) {
                    listfilter = new ArrayList<CatalogFilter>();
                    for (DataObject filter : list.getList()) {
                        CatalogFilter catalogFilter = new CatalogFilter();
                        catalogFilter.setCfiId(filter.getBigDecimal("CFI_ID"));
                        catalogFilter.setCfiLabel(filter.getString("CFI_LABEL"));
                        catalogFilter.setCfiColumn(filter.getString("CFI_COLUMN"));
                        listfilter.add(catalogFilter);
                    }
                }
                catalog.setCatalogFilterList(listfilter);

            }
            return catalog;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar el catálogo. EX000: " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param parameter
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public List<CatalogFilterReport> queryFilterReport(String parameter) throws ApplicationException {
        CatalogAdc catalog = null;

        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            List<CatalogFilterReport> listfilter = null;
            DataList list = adb.outDataList("rep1", parameter);
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                listfilter = new ArrayList<CatalogFilterReport>();
                for (DataObject filter : list.getList()) {
                    CatalogFilterReport catalogFilter = new CatalogFilterReport();
                    catalogFilter.setCfrId(filter.getBigDecimal("CFR_ID"));
                    catalogFilter.setCfrLabel(filter.getString("CFR_LABEL"));
                    catalogFilter.setCfrColumn(filter.getString("CFR_COLUMN"));
                    catalogFilter.setCreSqldata(filter.getString("CRE_SQLDATA"));
                    catalogFilter.setCreSqldata1(filter.getString("CRE_SQLDATA1"));
                    catalogFilter.setCreExportExcel(filter.getString("CRE_EXPORT_EXCEL"));
                    catalogFilter.setCreExportCvs(filter.getString("CRE_EXPORT_CVS"));
                    catalogFilter.setCreExportPdf(filter.getString("CRE_EXPORT_PDF"));
                    catalogFilter.setCfrType(filter.getString("CFR_TYPE"));
                    listfilter.add(catalogFilter);
                }
            }

            return listfilter;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar el filtro de reporte. EX000: " + e.getMessage(), e);
        }
    }

    /**
     * Ejecuta la consulta sobre la entidad segun filtro suministrado por el
     * usuario Pagina (2)
     *
     * @param ctl_sql
     * @param parameter
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public DataResult queryDataResult(String ctl_sql, String parameter[]) throws ApplicationException {
        DataResult dataresult = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataListIndex list = adb.outDataListIndex(ctl_sql, parameter);
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                dataresult = new DataResult();
                dataresult.setListNameColumn(list.getNameColumns());
                for (DataObjectIndex reg : list.getArrayList()) {
                    DataRow datarow = new DataRow();
                    for (Object data : reg.getData()) {
                        if (data != null) {
                            datarow.setColumn(data.toString());
                        } else {
                            datarow.setColumn("");
                        }
                    }
                    dataresult.addDataRow(datarow);
                }
            }
            return dataresult;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar la data resultante. EX000: " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param ctl_sql
     * @param parameter
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public DataResultquery queryDataResultquery(String ctl_sql, String parameter[]) throws ApplicationException {
        DataResultquery dataResultquery = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataListIndex list = adb.outDataListIndex(ctl_sql, parameter);
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                dataResultquery = new DataResultquery();
                dataResultquery.setListNameColumnq(list.getNameColumns());
                for (DataObjectIndex reg : list.getArrayList()) {
                    DataRow datarow = new DataRow();
                    for (Object data : reg.getData()) {
                        if (data != null) {
                            datarow.setColumn(data.toString());
                        } else {
                            datarow.setColumn("");
                        }
                    }
                    dataResultquery.addDataRow(datarow);
                }
            }
            return dataResultquery;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);            
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar la data resultante. EX000: " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param ctl_sql
     * @param parameter
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public DataRelaciones queryDataRelaciones(String ctl_sql, String parameter[]) throws ApplicationException {
        DataRelaciones dataRelaciones = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataListIndex list = adb.outDataListIndex(ctl_sql, parameter);
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                dataRelaciones = new DataRelaciones();
                dataRelaciones.setListNameColumnq(list.getNameColumns());
                for (DataObjectIndex reg : list.getArrayList()) {
                    DataRow datarow = new DataRow();
                    for (Object data : reg.getData()) {
                        if (data != null) {
                            datarow.setColumn(data.toString());
                        } else {
                            datarow.setColumn("");
                        }
                    }
                    dataRelaciones.addDataRow(datarow);
                }
            }
            return dataRelaciones;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);            
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar las relaciones. EX000: " + e.getMessage(), e);
        }
    }

    /**
     * Consulta configuracion de Catalogo para el despliegue de interfaz de
     * Crear,Modificar o Eliminar Consulta los datos del registro seleccionado
     * segun la entidad (no aplica si es crear) Pagina (3)
     *
     * @param ctl_id
     * @param parameter
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public DataMetaTable queryConfigCatalogData(BigDecimal ctl_id, String parameter[]) throws ApplicationException {
        DataMetaTable dataMetaTable = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            //consulta tabla y sql para (Insert,Update,Deletev) en la configuracion de catalogo 
            String[] parameters = new String[2];
            parameters[0] = parameter[0];
            parameters[1] = parameter[1];
            DataObject obj = adb.outDataObjec("ctl6", (Object[]) parameters);
            String id = parameter[0];
            String type = parameter[1];
            String idregistro = parameter[2];


            if (obj != null) {
                String table = obj.getString("CDT_TABLE");//cd.cdt_table, cd.cdt_sql
                String sql = obj.getString("CDT_SQL");

                BigDecimal cdtid = obj.getBigDecimal("CDT_ID");


                List<DataTable> listStruct = createDataTable(adb, table, cdtid, parameters);//consulta estructura en tablas del sistema

                if (listStruct != null) {
                    dataMetaTable = new DataMetaTable();
                    dataMetaTable.setButtonCreate(obj.getString("CDT_BCREATE"));
                    dataMetaTable.setButtonUpdate(obj.getString("CDT_BUPDATE"));
                    dataMetaTable.setButtonDelete(obj.getString("CDT_BDELETE"));
                    dataMetaTable.setSqlCreate(obj.getString("CDT_SQLCREATE"));
                    dataMetaTable.setSqlUpdate(obj.getString("CDT_SQLUPDATE"));
                    dataMetaTable.setSqlDelete(obj.getString("CDT_SQLDELETE"));
                    dataMetaTable.setCdtId(obj.getBigDecimal("CDT_ID"));
                    dataMetaTable.setTablealias(obj.getString("CDT_ALIAS"));
                    dataMetaTable.setTablename(table);

                    //nuevo 
                    String[] paramete = new String[1];
                    paramete[0] = parameter[0];
                    DataObject ob = adb.outDataObjec("ctl50", (Object[]) paramete);
                    if (ob != null) {
                        dataMetaTable.setRel(ob.getString("CDT_SQL"));

                        dataMetaTable.setRelacionalias(ob.getString("CDT_ALIAS"));
                    } else {
                        dataMetaTable.setRel("");
                    }
                    if ("".equals(idregistro) == true) {
                        idregistro = "0";
                    }
                    if ("0".equals(idregistro) == false) {//si id es diferente de null se consulta datos del respectivo registro
                        List<DataTable> datatable = queryDataEntity(adb, sql, idregistro, listStruct);
                        dataMetaTable.setDatatable(datatable);
                    } else {
                        List<DataTable> datatable = listStruct;
                        dataMetaTable.setDatatable(datatable);

                    }
                }
            }
            DireccionUtil.closeConnection(adb);

            return dataMetaTable;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);            
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar la configuración del catálogo. EX000: " + e.getMessage(), e);
        }

    }

    /**
     * Consulta caracteristicas de la tabla segun sistema de MDBS Pagina (3)
     *
     * @param adb
     * @param table
     * @return
     * @throws ExceptionDB
     */
    private List<DataTable> createDataTable(AccessData adb, String table, BigDecimal cdtid, String parameters[]) throws ExceptionDB {
        List<DataTable> listDatatable = null;
        DataList list_columns = adb.outDataList("ctl4", table);
        DataList list_fk = adb.outDataList("ctl5", table);

        String str = cdtid.toString();
        DataList list_restriccion = adb.outDataList("ctl59", str);
        DataList list_pk = adb.outDataList("ctl200", table);


        if (list_columns != null) {

            listDatatable = new ArrayList<DataTable>();
            for (DataObject obj : list_columns.getList()) {
                DataTable dataTable = new DataTable();

                String namecolumn = obj.getFormatString("COLUMN_NAME");
                dataTable.setNamesystem(obj.getFormatString("COLUMN_NAME"));
                dataTable.setName(obj.getFormatString("COMMENTS"));
                dataTable.setValue("");
                dataTable.setType(obj.getFormatString("DATA_TYPE"));
                String precision = "";
                precision = obj.getFormatString("DATA_PRECISION");


                String datalengh = "";
                if (precision == null) {
                    datalengh = obj.getFormatString("DATA_LENGTH");
                } else {
                    datalengh = obj.getFormatString("DATA_PRECISION");


                }
                dataTable.setLongitud(datalengh);
                dataTable.setIsnull(obj.getFormatString("NULLABLE"));
                dataTable.setNametTable("");
                dataTable.setCtlfk("");
                dataTable.setIsvisibility(Boolean.TRUE);
                if (list_restriccion != null) {
                    for (DataObject objRestr : list_restriccion.getList()) {
                        String nameRest = objRestr.getFormatString("CRE_COLUMN");
                        if (nameRest != null && namecolumn != null) {
                            if (nameRest.equals(namecolumn) && nameRest.equals("USU_ESTADO") == false) {
                                dataTable.setIsvisibility(Boolean.FALSE);
                                dataTable.setName(namecolumn);
                                dataTable.setTyper(objRestr.getFormatString("CRE_TYPE"));
                            }

                        }
                        if (namecolumn.equals("USU_ESTADO")) {
                            dataTable.setNametTable("MULTIVALOR");
                            String[] parameterrel = new String[3];
                            parameterrel[0] = "MULTIVALOR";
                            parameterrel[1] = parameters[0];
                            if (parameters[1].equals("3")) {
                                parameterrel[2] = "4";
                            } else {
                                if (parameters[1].equals("4")) {
                                    parameterrel[2] = "5";
                                } else {
                                    parameterrel[2] = "2";
                                }
                                DataObject ctl_idq = adb.outDataObjec("ctl15", (Object[]) parameterrel);
                                if (ctl_idq != null) {
                                    dataTable.setCtlfk(ctl_idq.getFormatString("CDT_SQL"));

                                }
                            }
                        }
                    }
                }

                if (list_pk != null) {
                    dataTable.setPk(Boolean.FALSE);
                    for (DataObject objPk : list_pk.getList()) {
                        String namePk = objPk.getFormatString("COLUMN_NAME");
                        if (namePk != null && namecolumn != null) {
                            if (namePk.equals(namecolumn)) {
                                dataTable.setPk(Boolean.TRUE);
                            }
                        }
                    }
                }
                if (list_fk != null) {//agregar fk
                    for (DataObject objFK : list_fk.getList()) {
                        String namefk = objFK.getFormatString("COLUMN_NAME");
                        if (namefk != null && namecolumn != null) {
                            if (namefk.equals(namecolumn)) {
                                dataTable.setNametTable(objFK.getFormatString("TABLE_NAME"));
                                String[] parameterrel = new String[3];
                                parameterrel[0] = objFK.getFormatString("TABLE_NAME");
                                parameterrel[1] = parameters[0];

                                if (parameters[1].equals("3")) {
                                    parameterrel[2] = "4";
                                } else {
                                    if (parameters[1].equals("4")) {
                                        parameterrel[2] = "5";
                                    } else {
                                        parameterrel[2] = "2";
                                    }
                                    DataObject ctl_idq = adb.outDataObjec("ctl15", (Object[]) parameterrel);
                                    if (ctl_idq != null) {
                                        dataTable.setCtlfk(ctl_idq.getFormatString("CDT_SQL"));
                                    }
                                }
                            }
                        }
                    }
                }

                listDatatable.add(dataTable);
            }
        }


        return listDatatable;
    }

    /**
     * Consulta y agrega los datos del registro seleccionado para modificar o
     * eliminar. Agrega el resultado a los objetos de la respectiva columna y
     * restringe las que no se encuentran, exceptuando las fk Pagina (3)
     *
     * @param adb
     * @param sql
     * @param id
     * @return
     * @throws ExceptionDB
     */
    private List<DataTable> queryDataEntity(AccessData adb, String sql, String id, List<DataTable> listStruct) throws ExceptionDB {
        List<DataTable> datatable = new ArrayList<DataTable>();
        DataObjectIndex register = adb.outDataObjecIndex(sql, id);


        for (int h = 0; h < listStruct.size(); h++) {
            DataTable dataTable = new DataTable();
            DataTable dataStruct = listStruct.get(h);
            String data = register.getFormatString(h);
            dataTable.setValue("");
            if (data != null) {
                if (dataStruct.getName().equals("Estado") && data.toString().equals("15")) {
                    dataTable.setValue("Activo");

                } else {
                    dataTable.setValue(data.toString());
                }

            }

            dataTable.setName(dataStruct.getName());
            dataTable.setNamesystem(dataStruct.getNamesystem());
            dataTable.setType(dataStruct.getType());
            dataTable.setLongitud(dataStruct.getLongitud());

            dataTable.setNametTable(dataStruct.getNametTable());
            dataTable.setIsnull(dataStruct.getIsnull());
            dataTable.setCtlfk(dataStruct.getCtlfk());
            dataTable.setIsvisibility(dataStruct.getIsvisibility());
            dataTable.setPk(dataStruct.getPk());

            if (dataStruct.getCtlfk().equals("") == false) {
                if (dataStruct.getNametTable().equals("MULTIVALOR") == false) {
                    String[] subtabla = dataStruct.getNamesystem().split("_");
                    String[] parametername = new String[4];
                    parametername[0] = subtabla[0];
                    parametername[1] = dataStruct.getNametTable();
                    parametername[2] = dataStruct.getNamesystem();
                    parametername[3] = dataTable.getValue();
                    dataTable.setNamecamporel("");
                    DataObject ctl_nombrecampo = adb.outDataObjec("ctl132", (Object[]) parametername);
                    if (ctl_nombrecampo != null) {
                        String camp = subtabla[0] + "_NOMBRE";
                        dataTable.setNamecamporel(ctl_nombrecampo.getFormatString(camp));
                    }
                }
            }
            datatable.add(dataTable);

        }


        return datatable;
    }

    /**
     * Inserta un registro de una entidad
     *
     * @param ctl_sql
     * @param parameter
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public String insertEntity(String ctl_sql, String parameter[]) throws ApplicationException {
        return processIUD1(ctl_sql, parameter, "CREATE");


    }

    /**
     * Actualiza datos en un registro de una entidad
     *
     * @param ctl_sql
     * @param parameter
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public String updateEntity(String ctl_sql, String parameter[]) throws ApplicationException {
        return processIUD1(ctl_sql, parameter, "UPDATE");
    }

    /**
     * Elimina un registro de una entidad
     *
     * @param ctl_sql
     * @param parameter
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public String deleteEntity(String ctl_sql, String parameter[]) throws ApplicationException {
        return processIUD1(ctl_sql, parameter, "DELETE");
    }

    /**
     * Ejecuta operacion de inser, update o delete de cualquier en entidad en
     * base de datos
     *
     * @param ctl_sql
     * @param parameter
     * @param operacion
     * @return
     * @throws ExceptionDB
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public String processIUD(String ctl_sql, String parameter[], String operacion) throws ExceptionDB, ApplicationException {
        String message = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            CatalogAdc catalogo = new CatalogAdc();
            boolean ok = adb.in(ctl_sql, (Object[]) parameter);
            DireccionUtil.closeConnection(adb);
            if (ok) {
                return MESSAGE_OK;
            } else {
                return MESSAGE_ERROR;
            }
        } catch (ExceptionDB exdb) {
            message = exdb.getMessage();
            LOGGER.error(exdb.getMessage());
            DireccionUtil.closeConnection(adb);
            throw exdb;
        } catch (Exception e) {
            message = e.getMessage();
            LOGGER.error(e.getMessage());
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de procesar el IUD. EX000: " + e.getMessage(), e);
        }
    }

    /**
     * Ejecuta operacion de inser, update o delete de cualquier en entidad en
     * base de datos
     *
     * @param ctl_sql
     * @param parameter
     * @param operacion
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public String processIUD1(String ctl_sql, String parameter[], String operacion) throws ApplicationException {
        String message = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            boolean ok = adb.in(ctl_sql, (Object[]) parameter);
            if (ok) {
                AuditoriaEJB audit = new AuditoriaEJB();
                String data = "parameter:";
                for (int l = 0; l < parameter.length; l++) {
                    data = data + parameter[l] + ",";


                }
                audit.auditarCatalogo("CATALOGO", "CATALOGO", "naty", operacion, data, adb);
                message = MESSAGE_OK;
            } else {
                message = MESSAGE_ERROR;
            }
            DireccionUtil.closeConnection(adb);
            return message;
        } catch (ExceptionDB e) {
            message = e.getMessage();
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de procesar el IUD. EX000: " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param ctl_sql
     * @param parameter
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws ExceptionDB
     */
    @Override
    public BigDecimal countResul(String ctl_sql, String[] parameter) throws ApplicationException {
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec(ctl_sql, (Object[]) parameter);
            DireccionUtil.closeConnection(adb);
            return obj.getBigDecimal("CNT");
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);            
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de realizar el conteo del resultado. EX000: " + e.getMessage(), e);
        }
    }
}