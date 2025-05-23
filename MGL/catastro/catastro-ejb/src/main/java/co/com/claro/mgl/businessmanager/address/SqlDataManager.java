/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.SqlDataDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.SqlData;
import com.jlcg.db.exept.ExceptionDB;
import com.jlcg.db.sql.BuildSQL;
import java.util.List;

/**
 *
 * @author User
 */
public class SqlDataManager {

    SqlDataDaoImpl sqlDataDaoImpl = new SqlDataDaoImpl();

    public List<SqlData> findAll() throws ApplicationException {
        List<SqlData> result;
        result = sqlDataDaoImpl.findAll();
        return result;
    }

    public SqlData update(SqlData sqlData) throws ApplicationException, ExceptionDB {
        SqlData resutl = sqlDataDaoImpl.update(sqlData);
        BuildSQL.load();
        return resutl;


    }

    public SqlData create(SqlData sqlData) throws ApplicationException, ExceptionDB {

        SqlData resutl = sqlDataDaoImpl.create(sqlData);
        BuildSQL.load();
        return resutl;
    }

    public boolean delete(SqlData sqlData) throws ApplicationException, ExceptionDB {

        boolean resutl = sqlDataDaoImpl.delete(sqlData);
        BuildSQL.load();
        return resutl;
    }

    public SqlData findById(SqlData sqlData) throws ApplicationException {

        return null;

    }

    public List<SqlData> findSqlDataById(String idSqlData) throws ApplicationException {
       
               List<SqlData> result;
        result = sqlDataDaoImpl.findSqlDataById(idSqlData);
        return result;
       
       
    }
}
