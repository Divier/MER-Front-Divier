package co.com.telmex.catastro.services.comun;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.CatalogAdc;
import co.com.telmex.catastro.data.CatalogFilterReport;
import co.com.telmex.catastro.data.DataMetaTable;
import co.com.telmex.catastro.data.DataResult;
import co.com.telmex.catastro.data.DataResultquery;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 * Interfase CatalogEJBRemote
 * implementa Serialización
 *
 * @author 	Deiver Rovira.
 * @version	1.0
 */
@Remote
public interface CatalogEJBRemote {

    /**
     * 
     * @param name
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<CatalogAdc> queryListCatalog(String name) throws ApplicationException;

    /**
     * 
     * @param ctl_id
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public CatalogAdc queryCatalog(BigDecimal ctl_id) throws ApplicationException;

    /**
     * 
     * @param ctl_sql
     * @param parameter
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public DataResult queryDataResult(String ctl_sql, String parameter[]) throws ApplicationException;

    /**
     * 
     * @param ctl_sql
     * @param parameter
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public DataResultquery queryDataResultquery(String ctl_sql, String parameter[]) throws ApplicationException;

    /**
     * 
     * @param parameter
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<CatalogFilterReport> queryFilterReport(String parameter) throws ApplicationException;

    /**
     * 
     * @param ctl_id
     * @param parameter
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public DataMetaTable queryConfigCatalogData(BigDecimal ctl_id, String parameter[]) throws ApplicationException;

    /**
     * 
     * @param ctl_sql
     * @param parameter
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public String insertEntity(String ctl_sql, String parameter[]) throws ApplicationException;

    /**
     * 
     * @param ctl_sql
     * @param parameter
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public String updateEntity(String ctl_sql, String parameter[]) throws ApplicationException;

    /**
     * 
     * @param ctl_sql
     * @param parameter
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public String deleteEntity(String ctl_sql, String parameter[]) throws ApplicationException;

    /**
     * 
     * @param ctl_sql
     * @param parameter
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public BigDecimal countResul(String ctl_sql, String parameter[]) throws ApplicationException;
}
