/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.SqlDataManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.SqlData;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author User
 */
@Stateless
public class SqlDataFacade implements SqlDataFacadeLocal {

    private static final Logger LOGGER = LogManager.getLogger(SqlDataFacade.class);
    private final SqlDataManager sqlDataManager;

    public SqlDataFacade() {
        this.sqlDataManager = new SqlDataManager();
    }

    @Override
    public List<SqlData> findAll() throws ApplicationException {
        return sqlDataManager.findAll();
    }

    @Override
    public SqlData create(SqlData sqlData) throws ApplicationException {
        try {
            return sqlDataManager.create(sqlData);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg,  e);
        }
    }

    @Override
    public SqlData update(SqlData sqlData) throws ApplicationException {
        try {
            return sqlDataManager.update(sqlData);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg,  e);
        }

    }

    @Override
    public boolean delete(SqlData sqlData) throws ApplicationException {
        try {
            return sqlDataManager.delete(sqlData);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
        }
        return false;

    }

    @Override
    public SqlData findById(SqlData sqlData) throws ApplicationException {
        return sqlDataManager.findById(sqlData);
    }

    @Override
    public List<SqlData> findSqlDataById(String idSqlData)  {
     try {
            return sqlDataManager.findSqlDataById(idSqlData);
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
        }
     return null;
    }
}
