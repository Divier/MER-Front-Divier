package co.com.telmex.catastro.delegate;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.CatalogAdc;
import co.com.telmex.catastro.data.CatalogFilterReport;
import co.com.telmex.catastro.data.DataMetaTable;
import co.com.telmex.catastro.data.DataResult;
import co.com.telmex.catastro.data.DataResultquery;
import co.com.telmex.catastro.services.comun.CatalogEJBRemote;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * ComunDelegate
 * implementa Serialización
 *
 * @author 	Jose Luis Caicedo G.
 * @version	1.0
 */
public class ComunDelegate {
    
    private static final Logger LOGGER = LogManager.getLogger(ComunDelegate.class);

    private static String CATALOGEJB = "catalogEJB#co.com.telmex.catastro.services.comun.CatalogEJBRemote";

    /**
     * 
     * @return
     * @throws javax.naming.NamingException
     */
    public static CatalogEJBRemote getCatalogEjb() throws NamingException {
        InitialContext ctx = new InitialContext();
        CatalogEJBRemote obj = (CatalogEJBRemote) ctx.lookup(CATALOGEJB);
        return obj;
    }

    /**
     * 
     * @param nameuser
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static List<CatalogAdc> queryListCatalog(String nameuser) throws ApplicationException {
        List<CatalogAdc> list = null;
        try {
            CatalogEJBRemote obj = getCatalogEjb();
            if (obj != null) {
                list = obj.queryListCatalog(nameuser);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar la lista del catalogo. EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error al consultar la lista del catalogo. EX000 " + ex.getMessage(), ex);
        }
        return list;
    }

    /**
     * 
     * @param parameter
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static List<CatalogFilterReport> queryFilterReport(String parameter) throws ApplicationException {
        List<CatalogFilterReport> filterReport = null;
        try {
            CatalogEJBRemote obj = getCatalogEjb();
            if (obj != null) {

                filterReport = obj.queryFilterReport(parameter);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar el filtro del reporte. EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error al consultar el filtro del reporte. EX000 " + ex.getMessage(), ex);
        }
        return filterReport;
    }

    /**
     * 
     * @param ctl_id
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static CatalogAdc queryCatalog(BigDecimal ctl_id) throws ApplicationException {
        CatalogAdc catalogAdc = null;
        try {
            CatalogEJBRemote obj = getCatalogEjb();
            if (obj != null) {
                catalogAdc = obj.queryCatalog(ctl_id);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar el catálogo. EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error al consultar el catálogo. EX000 " + ex.getMessage(), ex);
        }
        return catalogAdc;
    }

    /**
     * 
     * @param ctl_sql
     * @param parameter
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static DataResult queryDataResult(String ctl_sql, String parameter[]) throws ApplicationException {
        DataResult dataResult = null;
        try {
            CatalogEJBRemote obj = getCatalogEjb();
            if (obj != null) {
                dataResult = obj.queryDataResult(ctl_sql, parameter);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar el resultado. EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error al consultar el resultado. EX000 " + ex.getMessage(), ex);
        }
        return dataResult;
    }

    /**
     * 
     * @param ctl_sql
     * @param parameter
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static DataResultquery queryDataResultq(String ctl_sql, String parameter[]) throws ApplicationException {
        DataResultquery dataResultquery = null;
        try {
            CatalogEJBRemote obj = getCatalogEjb();
            if (obj != null) {
                dataResultquery = obj.queryDataResultquery(ctl_sql, parameter);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar los datos resultantes. EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error al consultar los datos resultantes. EX000 " + ex.getMessage(), ex);
        }
        return dataResultquery;
    }

    /**
     * 
     * @param ctl_id
     * @param parameter
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static DataMetaTable queryDataMetaTable(BigDecimal ctl_id, String parameter[]) throws ApplicationException {
        DataMetaTable dataMetaTable = null;
        try {
            CatalogEJBRemote obj = getCatalogEjb();
            if (obj != null) {

                dataMetaTable = obj.queryConfigCatalogData(ctl_id, parameter);

            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar datos de la meta tabla. EX000 " + ex.getMessage(), ex);
        } catch (Exception ex) {
            LOGGER.error("Error al consultar datos de la meta tabla. EX000 " + ex.getMessage(), ex);
        }
        return dataMetaTable;
    }
}
