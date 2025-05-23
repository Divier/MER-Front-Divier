package com.jlcg.db.sql;

import com.jlcg.db.AccessData;
import com.jlcg.db.exept.ExceptionDB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javax.sql.DataSource;

public class ManageAccess {

    private static DataSource datasource = null;
    private static String driver = null;
    private static String url = null;
    private static String user = null;
    private static String pwd = null;

    public static boolean init(DataSource datasourceP) throws ExceptionDB {
        boolean ok = false;
        if (datasourceP == null) {
            throw new ExceptionDB(MessageSQL.MESSAGE_DATASOURCE_NULL);
        } else {
            datasource = datasourceP;
            ok = true;
        }
        return ok;
    }

    public static boolean init(Properties properties) throws ExceptionDB {
        boolean ok = false;
        if (properties == null) {
            throw new ExceptionDB(MessageSQL.MESSAGE_PROPERTIES_NULL);
        }
        driver = properties.getProperty("driver");
        url = properties.getProperty("url");
        user = properties.getProperty("user");
        pwd = properties.getProperty("pwd");
        if (driver == null || url == null || user == null || pwd == null) {
            throw new ExceptionDB(MessageSQL.MESSAGE_NOTPARAMETER);
        } else {
            ok = true;
        }
        return ok;
    }

    public static AccessData createAccessData() throws ExceptionDB {
        AccessData accessData = null;
        try {
            Connection cn = getConnection();
            if (cn == null) {
                throw new ExceptionDB(MessageSQL.MESSAGE_ACCESSDATA);
            }
            accessData = new AccessData(cn);
        } catch (ExceptionDB exc) {
            throw exc;
        }
        return accessData;
    }

    public static boolean ping() throws ExceptionDB {
        boolean ok = false;
        if (getConnection() != null) {
            ok = true;
        }
        return ok;
    }

    protected static Connection getConnection() throws ExceptionDB {
        Connection cn = null;
        try {
            if (datasource != null) {
                cn = datasource.getConnection();
            } else if (driver != null && url != null && user != null && pwd != null) {
                try {
                    Class.forName(driver);
                    cn = DriverManager.getConnection(url, user, pwd);
                } catch (ClassNotFoundException exc) {
                    throw new ExceptionDB((new StringBuilder(String.valueOf(MessageSQL.MESSAGE_DRIVER))).append(exc.getMessage()).toString());
                } catch (SQLException exc) {
                    throw new ExceptionDB((new StringBuilder(String.valueOf(MessageSQL.MESSAGE_CONNECTION))).append(exc.getMessage()).toString());
                }
            } else {
                throw new ExceptionDB(MessageSQL.MESSAGE_DATASOURCE_AND_PROPERTIES_ISNULL);
            }
        } catch (SQLException exc) {
            throw new ExceptionDB((new StringBuilder(String.valueOf(MessageSQL.MESSAGE_CONNECTION))).append(exc.getMessage()).toString());
        }
        return cn;
    }

    public static DataSource getDatasource() {
        return datasource;
    }
}
